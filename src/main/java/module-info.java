module core {
    exports com.github.springbotplatform.core;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;
    requires spring.web;

    requires org.slf4j;

    requires telegrambots.meta;
    requires telegrambots;
    requires lombok;
//    requires lombok;


}