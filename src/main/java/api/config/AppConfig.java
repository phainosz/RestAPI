package api.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * Configuração de caminho inicia para a API REST
 * 
 * @author E804684
 *
 */
@ApplicationPath("api")
public class AppConfig extends Application {

	public AppConfig() {
		BeanConfig conf = new BeanConfig();
		conf.setTitle("Projeto Base API");
		conf.setDescription("Projeto base de APIs em Rest");
		conf.setVersion("1.0.0");
		conf.setHost("localhost:8080");
		conf.setBasePath("/projeto/api");
		conf.setSchemes(new String[] { "http" });
		conf.setResourcePackage("api.endpoint");
		conf.setScan(true);
	}
}
