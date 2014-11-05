package com.hehua.item;

import com.hehua.item.domain.Brand;
import com.hehua.item.service.BrandService;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by hewenjerry on 14-9-17.
 */
public class TestMultiDAO extends TestCase {

    private ClassPathXmlApplicationContext applicationContext;

    @Override
    public void setUp() {
        applicationContext = new ClassPathXmlApplicationContext(
                "classpath*:/spring/applicationContext*.xml");
    }

    @Test
    public void testBrandService() {
        BrandService brandService = applicationContext.getBean(BrandService.class);
        Brand brand = new Brand();
        brand.setCts(new Date());
        brand.setUts(new Date());
        brand.setName("test brandName");
        brand.setDesc("test desc");
        brandService.createBrand(brand);

        brand = brandService.getBrandById(brand.getId());
        System.out.println(brand.getName() + "   desc=" + brand.getDesc());

        System.out.println(brandService.getAllBrand());

    }

}
