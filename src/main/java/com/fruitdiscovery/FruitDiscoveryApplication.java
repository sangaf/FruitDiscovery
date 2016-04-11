package com.fruitdiscovery;

import com.fruitdiscovery.service.ProductInfoConverter;
import com.fruitdiscovery.service.ProductInfoDiscoveryService;

import java.io.IOException;
import java.text.ParseException;

public class FruitDiscoveryApplication {

	/**
	 * To run the application requires only one argument which needs to be a valid url of a HTML page
	 * From that HTML page application discovers the products and prints a json output
	 *
	 * @param args list of arguments
	 * @throws IOException
	 * @throws ParseException
     */
	public static void main(String[] args) throws IOException, ParseException {

		if(args.length != 1){
			System.out.println("To run this system requires a argument which needs to be a valid url");
			System.exit(1);
		}

		ProductInfoDiscoveryService productDiscoveryService = new ProductInfoDiscoveryService();
		ProductInfoConverter productConverter = new ProductInfoConverter();
		String jsonOutput = productConverter.getJSONString(productDiscoveryService.getProducts(args[0]));

		System.out.println(jsonOutput);

	}

}
