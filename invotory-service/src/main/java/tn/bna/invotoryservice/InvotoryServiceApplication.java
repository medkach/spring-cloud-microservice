package tn.bna.invotoryservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
}
@RepositoryRestResource(/*excerptProjection = ProjectionProduct.class*/)
//------------------------- extends CrudRepository
interface ProductRepository extends JpaRepository<Product,Long>{

}
@Projection(name = "projectionProduct" ,types = {Product.class})
interface ProjectionProduct {
    @Value("#{target.id}")
    Long getId();
    String getName();
}
@SpringBootApplication
public class InvotoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvotoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository)
    {
      return  args -> {
          productRepository.save(new Product(null,"pc Toshiba",1500)) ;
          productRepository.save(new Product(null,"pc Dell",1900)) ;
          productRepository.save(new Product(null,"pc lenovo",2000)) ;
          productRepository.findAll().forEach(System.out::println);

        };
    }

}
