/**
 * 
 */
package com.hehua.item.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.hehua.commons.Filters;
import com.hehua.commons.time.DateUtils;
import com.hehua.item.domain.Flash;
import com.hehua.item.enums.Gender;
import com.hehua.item.utils.FlashUtils;
import com.hehua.user.domain.BabyStage;

/**
 * @author zhihua
 *
 */
public class FlashSession {

    private static Logger log = Logger.getLogger(FlashSession.class.getName());

    private Date sessionDate;

    private Date startTime;

    private Date endTime;

    private List<Flash> flashes;

    private Map<Integer, FlashEntryList> flashesListMapByBabyPopulation = new HashMap<>();

    private Map<Integer, Integer> flashIdMaxCrowdMap;

    private Map<Integer, Integer> flashIdMinCrowdMap;

    private Map<Integer, Flash> itemFlashMapping;

    /**
     * 根据性别和宝宝年龄计算适用人群索引
     * 
     * @param gender
     * @param babyStage
     * @return
     */
    public static int calculatePopulation(Gender gender, BabyStage babyStage) {
        return babyStage.getCode() * 10 + gender.getCode();
    }

    public FlashEntryList getFlashEntryListByPop(int population) {
        return flashesListMapByBabyPopulation.get(population);
    }

    /**
     * 初始化人群商品映射表 see @FlashManager
     * 
     * @param flashs
     */
    public void prepare(List<Flash> flashs) {
        this.flashes = flashs;

        this.flashIdMaxCrowdMap = FlashUtils.getFlashMapByCrowd(flashs, true);
        this.flashIdMinCrowdMap = FlashUtils.getFlashMapByCrowd(flashs, false);
        for (Gender gender : Gender.values()) {
            for (BabyStage babyStage : BabyStage.values()) {
                FlashEntryList flashList = buildFlashList(gender, babyStage, flashs);
                flashesListMapByBabyPopulation.put(flashList.getPopulation(), flashList);
                log.info("population=" + flashList.getPopulation() + ",size="
                        + flashList.getFlashEntries().size());
            }
        }
    }

    /**
     * 构建适合某一人群的商品列表
     * 
     * @param gender
     * @param babyStage
     * @param flashs
     * @return
     */
    private FlashEntryList buildFlashList(final Gender gender, final BabyStage babyStage,
            List<Flash> flashs) {
        /**
         * 根据性别和适合人群过滤
         */
        List<Flash> filteredList = Filters.filter(flashs, new Predicate<Flash>() {

            @Override
            public boolean apply(Flash input) {
                //System.out.println("before,flashid=" + input.getId() + " isGroup=" + input.isGroup());
                int maxCrowd = flashIdMaxCrowdMap.get(input.getId()); // 1、备孕，包含怀孕 2、0-1岁 3、1-2岁 4、2-3岁

                //团购flash直接返回
                if (maxCrowd == FlashUtils.GROUP_CROWD) {
                    return true;
                }

                if (maxCrowd < babyStage.getCode()) {
                    return false;
                }

                //包含备孕，不检查性别
                if (flashIdMinCrowdMap.get(input.getId()) == 1 || input.getGender() == 4 || gender == Gender.Unkown) {
                    return true;
                } else {
                    return input.getGender() == gender.getCode();
                }
            }
        });

        /**
         * 排序规则：品牌团优先，babyStage小优先
         */
        Comparator<Flash> comparator = new Comparator<Flash>() {

            @Override
            public int compare(Flash o1, Flash o2) {
                int crowds1 = flashIdMinCrowdMap.get(o1.getId());
                int crowds2 = flashIdMinCrowdMap.get(o2.getId());
                return ComparisonChain.start().compare(crowds1, crowds2).compare(o2.getPriority(), o1.getPriority()).result();
            }
        };
        Collections.sort(filteredList, comparator);

        FlashEntryList flashList = new FlashEntryList();
        List<FlashEntry> flashEntries = new ArrayList<>(filteredList.size());
        for (int i = 0; i < filteredList.size(); i++) {
            FlashEntry flashEntry = new FlashEntry();
            flashEntry.setFlash(filteredList.get(i));
            flashEntry.setCrowd(flashIdMinCrowdMap.get(filteredList.get(i).getId()));
            if (i != 0) {
                flashEntry.setPreCrow(flashIdMinCrowdMap.get(filteredList.get(i - 1).getId()));
            } else {
                flashEntry.setPreCrow(FlashUtils.GROUP_CROWD);
            }
            flashEntries.add(flashEntry);
            //System.out.println("after,flashid=" + flashEntry.getFlash().getId() + " isgroup=" + flashEntry.getFlash().isGroup()
            //    + " precrowd=" + flashEntry.getPreCrow() + " crowd=" + flashEntry.getCrowd());
        }
        int population = calculatePopulation(gender, babyStage);
        flashList.setPopulation(population);
        flashList.setFlashEntries(flashEntries);
        return flashList;
    }

    /**
     * @param sessionDate
     * @param startTime
     * @param endTime
     */
    public FlashSession(Date sessionDate, Date startTime, Date endTime) {
        super();
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 当期闪购是否今天的
     * 
     * @return
     */
    public boolean isSessionToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar = DateUtils.truncate(calendar, Calendar.DATE);
        if (hour < 10) {
            return calendar.getTime().getTime() == sessionDate.getTime() + 86400 * 1000;
        } else {
            return calendar.getTime().getTime() == sessionDate.getTime();
        }
    }

    public List<Flash> getFlashes() {
        return flashes;
    }

    public void setFlashes(List<Flash> flashes) {
        this.flashes = flashes;
    }

    public Map<Integer, Flash> getItemFlashMapping() {
        return itemFlashMapping;
    }

    public void setItemFlashMapping(Map<Integer, Flash> itemFlashMapping) {
        this.itemFlashMapping = itemFlashMapping;
    }

    @Override
    public String toString() {
        return "FlashSession [sessionDate=" + sessionDate + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

    public static void main(String[] args) {
        FlashSession flashSession = new FlashSession(new Date(), new Date(), new Date());
        Calendar calendar = Calendar.getInstance();

         calendar = DateUtils.truncate(calendar, Calendar.DATE);
        System.out.println(DateUtils.formatDateTime(calendar.getTime()));

    }

}
