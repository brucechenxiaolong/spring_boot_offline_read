package com.pde.pdes.offline.config;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

//@Configuration
public class DbScriptInit {
	
	@Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) throws IOException {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(this.databasePopulator());
        return initializer;
    }

    /**
     * 初始化数据资源
     * 
     * @author FYK
     * @return org.springframework.core.io.Resource[] 资源对象
     */
    private Resource[] getResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:db/*.sql");
        if (resources.length > 0) {
            File file = new File(resources[0].getFile().getParent()+"/success.txt");
            if (file.exists()) {
                return new Resource[0];
            }
        }
        //log.info("加载初始化脚本文件---------start");
        for (Resource resource : resources) {
            System.out.println(resource.getFilename());
            //删除脚本，保证数据安全，以及防止再次执行
            File file = new File(resource.getFile().getParent()+"/success.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        }
       // log.info("加载初始化脚本文件---------end");
        return resources;
    }

    /**
     * 初始化数据策略
     * 
     * @author FYK
     * @return org.springframework.jdbc.datasource.init.DatabasePopulator 策略对象
     */
    private DatabasePopulator databasePopulator() throws IOException {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(this.getResources());
        return populator;
    }



}
