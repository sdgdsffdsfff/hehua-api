/**
 * 
 */
package com.hehua.item.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hehua.commons.time.DateUtils;
import com.hehua.item.domain.Crowd;
import com.hehua.item.domain.Gender;
import com.hehua.user.domain.Baby;
import com.hehua.user.domain.BabyStage;
import com.hehua.user.model.BabyStatus;

/**
 * @author zhihua
 *
 */
public class BabyUtils {

    public static final int FLASH_TYPE_GROUP = 2;

    public static final int FLASH_TYPE_ITEM = 1;

    public static int getCrowd(Baby baby) {
        switch (baby.getStatus()) {
            case 0:
                // 备孕
                return 1;
            case 1:
                // 怀孕
                return 1;
            case 2:
                // 已出生
                int age = calculateAge(baby.getBirthday());
                if (age <= 1) {
                    return 2;
                } else if (age <= 2) {
                    return 3;
                } else if (age <= 3) {
                    return 4;
                }
                return 4;
            case -1: // 未选
                return 1;
            default:
                throw new RuntimeException("unkown baby status " + baby.getStatus());
        }
    }

    public static BabyStage getBabyStage(Baby baby) {
        switch (baby.getStatus()) {
            case 0:
            case 1:
                return BabyStage.Preparing;
            case 2:
                int age = calculateAge(baby.getBirthday());
                if (age <= 0) {
                    return BabyStage.ZeroYear;
                } else if (age <= 1) {
                    return BabyStage.OneYear;
                } else {
                    return BabyStage.TwoYear;
                }
            case -1:
                return BabyStage.Preparing;
            default:
                throw new RuntimeException("unkown baby status " + baby.getStatus());
        }
    }

    public static int getPregnantMonth(Baby baby) {
        return getPregnantDays(baby) / 30;
    }

    public static int getPregnantDays(Baby baby) {
        int intervalDays = (int) DateUtils.intervalDays(baby.getEdc(), new Date());
        intervalDays = Math.max(0, intervalDays);

        int pregnantDays = 280 - intervalDays;
        pregnantDays = Math.max(0, pregnantDays);
        return pregnantDays;
    }

    public static int getBirthDays(Baby baby) {
        int intervalDays = (int) DateUtils.intervalDays(new Date(), baby.getBirthday());
        intervalDays = Math.max(0, intervalDays);
        return intervalDays;
    }

    public static String convertIdToStr(String babyIds, List<Crowd> crowdList) {
        String[] babyIdArray = StringUtils.split(babyIds, ",");
        List<String> stringList = new ArrayList<String>(babyIdArray.length);
        for (String str : babyIdArray) {
            for (Crowd crowd : crowdList) {
                if (crowd.getId() == Integer.valueOf(str).intValue()) {
                    stringList.add(crowd.getName());
                    continue;
                }
            }
        }

        return StringUtils.join(stringList, ",");
    }

    public static String convertGenderIdToStr(int genderId, List<Gender> genderList) {
        for (Gender gender : genderList) {
            if (genderId == gender.getId()) {
                return gender.getName();
            }
        }

        return "男";
    }

    public static String getHomePromptMessage(Baby baby) {
        BabyStatus status = BabyStatus.fromCode(baby.getStatus());
        switch (status) {
            case Birth:
                return getHomePromptMessageOfBirth(baby);
            case Pregnant:
                return "专为怀孕的您推荐";
            case Preparing:
                return "专为备孕的您推荐";
            case None:
            default:
                return "专为您推荐";
        }
    }

    private static String getHomePromptMessageOfBirth(Baby baby) {
        Calendar now = Calendar.getInstance();
        now = DateUtils.truncate(now, Calendar.DATE);

        Calendar birthday = Calendar.getInstance();
        birthday.setTime(baby.getBirthday());
        birthday = DateUtils.truncate(birthday, Calendar.DATE);

        int nowYear = now.get(Calendar.YEAR);
        int birthYear = birthday.get(Calendar.YEAR);

        int nowMonth = now.get(Calendar.MONTH);
        int birthMonth = birthday.get(Calendar.MONTH);

        int nowDay = now.get(Calendar.DATE);
        int birthDay = birthday.get(Calendar.DATE);

        int age = nowYear - birthYear;
        if (nowMonth < birthMonth) {
            age--;
        } else if (nowMonth == birthMonth && nowDay < birthDay) {
            age--;
        }

        // 润岁，没有生日
        Calendar lastBirthday = DateUtils.addYears(birthday, age);
        int days = (int) DateUtils.intervalDays(now, lastBirthday);
        if (age < 0) {
            return "您好像把宝宝生日选错了啊";
        }

        if (age == 0 && days <= 0) {
            return "专为刚出生宝宝推荐";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("专为您");

        if (age > 0) {
            stringBuilder.append(age).append("岁");
        }
        if (days > 0) {
            if (age > 0) {
                stringBuilder.append("零");
            }
            stringBuilder.append(days).append("天");
        }

        com.hehua.user.model.Gender gender = com.hehua.user.model.Gender.fromCode(baby.getGender());
        switch (gender) {
            case Female:
                stringBuilder.append("小公主");
                break;
            case Male:
                stringBuilder.append("小王子");
                break;
            case Unkown:
                break;
        }
        stringBuilder.append("推荐");
        return stringBuilder.toString();
    }

    public static String getAgeStr(Date birthdate) {
        Calendar now = Calendar.getInstance();
        now = DateUtils.truncate(now, Calendar.DATE);

        Calendar birthday = Calendar.getInstance();
        birthday.setTime(birthdate);
        birthday = DateUtils.truncate(birthday, Calendar.DATE);

        int nowYear = now.get(Calendar.YEAR);
        int birthYear = birthday.get(Calendar.YEAR);

        int nowMonth = now.get(Calendar.MONTH);
        int birthMonth = birthday.get(Calendar.MONTH);

        int nowDay = now.get(Calendar.DATE);
        int birthDay = birthday.get(Calendar.DATE);

        int age = nowYear - birthYear;
        if (nowMonth < birthMonth) {
            age--;
        } else if (nowMonth == birthMonth && nowDay < birthDay) {
            age--;
        }

        // 润岁，没有生日
        Calendar lastBirthday = DateUtils.addYears(birthday, age);
        int days = (int) DateUtils.intervalDays(now, lastBirthday);
        if (age < 0) {
            return "0岁";
        } else if (age == 0) {
            if (days <= 0) {
                return "出生";
            } else {
                return days + "天";
            }
        } else {
            if (days <= 0) {
                return age + "岁";
            } else {
                return age + "岁" + "零" + days + "天";
            }
        }
    }

    public static int calculateAge(Date date) {
        Calendar now = Calendar.getInstance();
        now = DateUtils.truncate(now, Calendar.DATE);

        Calendar birthday = Calendar.getInstance();
        birthday.setTime(date);
        birthday = DateUtils.truncate(birthday, Calendar.DATE);

        int nowYear = now.get(Calendar.YEAR);
        int birthYear = birthday.get(Calendar.YEAR);

        int nowMonth = now.get(Calendar.MONTH);
        int birthMonth = birthday.get(Calendar.MONTH);

        int nowDay = now.get(Calendar.DATE);
        int birthDay = birthday.get(Calendar.DATE);

        int age = nowYear - birthYear;
        if (nowMonth < birthMonth) {
            age--;
        } else if (nowMonth == birthMonth && nowDay < birthDay) {
            age--;
        }
        return Math.max(0, age);
    }

    public static void main(String[] args) {
        try {
            String[] array = { "2004-03-01", "2004-02-29", "2004-09-23", "2004-09-22",
                    "2004-09-24", "2014-09-23", "2014-09-22" };
            for (String string : array) {
                Baby baby = new Baby();
                baby.setStatus(com.hehua.user.model.BabyStatus.Birth.getCode());
                Date brithDay = org.apache.commons.lang3.time.DateUtils.parseDate(string,
                        "yyyy-MM-dd");
                baby.setBirthday(brithDay);
                System.out.println(getHomePromptMessage(baby));
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
