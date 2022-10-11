package zozo.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(annotationClass = Mapper.class, basePackages = "zozo")
@ComponentScan("zozo.interface_adaptor.repository")
public class MapperTestApplication {
}
