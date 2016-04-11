package com.fruitdiscovery.service;

import com.fruitdiscovery.domain.ProductInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductInfoDiscoveryService finds the product list from a HTML page by a given url
 *
 * Created by sanjidagafur on 10/04/2016.
 */
public class ProductInfoDiscoveryService {

    /**
    * Discover a list of product by searching a HTML page
    *
    * @param url a discovery url
    * @return returns a list of productInfo
    * @throws  IOException
    * @throws  ParseException
    **/
    public List<ProductInfo> getProducts(String url) throws IOException, ParseException {
        return getProductsFromPage(getHtmlPage(url));
    }

    /**
     *
     * @param document
     * @return list of product
     * @throws IOException
     * @throws ParseException
     */
    private List<ProductInfo> getProductsFromPage(Document document) throws IOException, ParseException {
        //getting the list elements containing link to product page
        ArrayList<Element> elements = findProductResourceUrl(document);

        List<ProductInfo> productInfos = new ArrayList<>();

        for (Element element : elements){
            //elements href attribute contains link to product page
            productInfos.add(getProductFromProductPage(getHtmlPage(element.attr("abs:href"))));

        }

        return productInfos;
    }

    /**
     *
     * @param document
     * @return individual product
     * @throws ParseException
     */
    private ProductInfo getProductFromProductPage(Document document) throws ParseException {
        //cssSelector .productTitleDescriptionContainer contains the title of the product
        String title = document.select(".productTitleDescriptionContainer").text();
        //size of the html page converting to kb
        String size = (document.body().text().length() / 1024) + "kb";


        String description = null;
        //cssSelector .productDataItemHeader contains the description
        List<Element> headerElements = document.select(".productDataItemHeader");
        for (Element element : headerElements) {
            final String text = element.text();
            switch (text) {
                case "Description":
                    //description text is inside the next element
                    description = element.nextElementSibling().text();
                    break;
            }
        }
        final String unitPriceText = document.select(".pricePerUnit").get(0).text();
        BigDecimal unitPrice = new BigDecimal(unitPriceText.substring((unitPriceText.indexOf("£") + 1), (unitPriceText.indexOf("/unit"))));

        return new ProductInfo(title, size, unitPrice, description);
    }

    /**
     * List of Element those hold the link of products
     *
     * @param document
     * @return ArrayList of HTML Element
     */
    private ArrayList<Element> findProductResourceUrl(Document document) {
        return document.select(".productInfo h3 a");
    }

    /**
     * Connect to the given url and get HTML
     *
     * @param url
     * @return HTML Document
     * @throws IOException
     */
    private Document getHtmlPage(String url) throws IOException {
        return Jsoup.connect(url).get();
    }


}
