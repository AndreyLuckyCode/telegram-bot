package com.andrey.blog.first_blog.aspects;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(public String mainPage(..))")
    public void beforeMainPageAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeInfoAspect: attempt to open Main Page");
        System.out.println("****************************************************");
    }
    @Before("execution(public String info(..))")
    public void beforeInfoAspect(){
        System.out.println("****************************************************");
        System.out.println("beforeInfoAspect: attempt to open info page");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlog(..))")
    public void beforeMainBlogAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeMainBlogAspect: attempt to open Blog page");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogPostAdd(..))")
    public void beforeFreeBlogPostAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogPostAddAspect: attempt to Add new Article");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogAdd(..))")
    public void beforeFreeBlogAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogAddAspect: attempt to open Add new post page");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogDetails(..))")
    public void beforeFreeBlogDetailsAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogDetailsAspect: attempt to read an Article");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogEdit(..))")
    public void beforeFreeBlogEditAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogEditAspect: attempt to edit an Article");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogPostUpdate(..))")
    public void beforeFreeBlogPostUpdateAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogPostUpdateAspect: attempt to update an Article");
        System.out.println("****************************************************");
    }
    @Before("execution(public String freeBlogPostDelete(..))")
    public void beforeFreeBlogPostDeleteAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - beforeFreeBlogPostDeleteAspect: attempt to delete an Article");
        System.out.println("****************************************************");
    }


    @After("execution(public String mainPage(..))")
    public void afterMainPageAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterMainPageAspect: Main Page is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String info(..))")
    public void afterInfoAspect(){
        System.out.println("****************************************************");
        System.out.println("afterInfoAspect: info page is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlog(..))")
    public void afterMainBlogAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterMainBlogAspect: Blog page is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogPostAdd(..))")
    public void afterFreeBlogPostAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogPostAddAspect: New Article is successfully added");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogAdd(..))")
    public void afterFreeBlogAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogAddAspect: Add new post page is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogDetails(..))")
    public void afterFreeBlogDetailsAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogDetailsAspect: Article is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogEdit(..))")
    public void afterFreeBlogEditAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogEditAspect: Edit page is successfully opened");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogPostUpdate(..))")
    public void afterFreeBlogPostUpdateAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogPostUpdateAspect: Article is successfully updated");
        System.out.println("****************************************************");
    }
    @After("execution(public String freeBlogPostDelete(..))")
    public void afterFreeBlogPostDeleteAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterFreeBlogPostDeleteAspect: Article is successfully deleted");
        System.out.println("****************************************************");
    }


    @AfterThrowing("execution(public String mainPage(..))")
    public void afterThrowingMainPageAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingMainPageAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String info(..))")
    public void afterThrowingInfoAspect(){
        System.out.println("****************************************************");
        System.out.println("afterThrowingInfoAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlog(..))")
    public void afterThrowingMainBlogAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingMainBlogAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogPostAdd(..))")
    public void afterThrowingFreeBlogPostAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogPostAddAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogAdd(..))")
    public void afterThrowingFreeBlogAddAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogAddAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogDetails(..))")
    public void afterThrowingFreeBlogDetailsAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogDetailsAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogEdit(..))")
    public void afterThrowingFreeBlogEditAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogEditAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogPostUpdate(..))")
    public void afterThrowingFreeBlogPostUpdateAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogPostUpdateAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
    @AfterThrowing("execution(public String freeBlogPostDelete(..))")
    public void afterThrowingFreeBlogPostDeleteAspect(){
        System.out.println("****************************************************");
        System.out.println("Logging - afterThrowingFreeBlogPostDeleteAspect: Error. An exception is thrown!");
        System.out.println("****************************************************");
    }
}
