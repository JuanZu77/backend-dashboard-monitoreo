package com.juan_zubiri.monitoreo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication(scanBasePackages = "com.juan_zubiri.monitoreo")
public class ChallengeMonitoreoApplication {

	public static void main(String[] args) {
		
		// Cargar las variables de entorno desde el archivo .env
        Dotenv dotenv = Dotenv.load();
        
        // Verifica si la variable se cargó correctamente
        System.out.println("URL:" + dotenv.get("DB_URL"));  // Esto debería imprimir la URL de la base de datos
        System.out.println("NOMBRE: " + dotenv.get("DB_USERNAME"));
        System.out.println("PASS: " + dotenv.get("DB_PASSWORD"));
        
		SpringApplication.run(ChallengeMonitoreoApplication.class, args);
	}

}
