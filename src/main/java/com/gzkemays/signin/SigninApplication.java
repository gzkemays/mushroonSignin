package com.gzkemays.signin;

        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.boot.builder.SpringApplicationBuilder;
        import org.springframework.boot.web.servlet.ServletComponentScan;
        import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
        import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.gzkemays.signin.mapper")
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@ServletComponentScan
public class SigninApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SigninApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(SigninApplication.class, args);
    }

}
