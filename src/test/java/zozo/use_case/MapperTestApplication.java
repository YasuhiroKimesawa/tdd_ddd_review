package zozo.use_case;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(annotationClass = Mapper.class, basePackages = "zozo")
@ComponentScan({"zozo.interface_adaptor.repository", "zozo.use_case"})
public class MapperTestApplication {
}
