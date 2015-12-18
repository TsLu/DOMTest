package demo;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.print.Book;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luts on 2015/12/18.
 */
public class DOMTest {
    public static void main(String[] args){

        List<Books> books = new ArrayList<Books>();

        Books book = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse("src/books.xml");
            //找到根节点
            Element root = document.getDocumentElement();

            System.out.println("------开始解析-------");
            NodeList booksList = root.getElementsByTagName("book");
            System.out.println("一共有" + booksList.getLength() + "本书");
            //遍历book节点的所有节点
            for (int i = 0; i < booksList.getLength(); i++){
                book = new Books();

                System.out.println("-----现在开始遍历第" +(i + 1) +  "本书的内容-----");
                //通过item(i)方法获取一个book节点
                Node nodeBook = booksList.item(i);
                //获取book节点的所有属性集合
                NamedNodeMap attrs = nodeBook.getAttributes();
                System.out.println("第" + (i + 1) + "本书有"+ attrs.getLength() + "个属性");

                //遍历book属性
                for (int j = 0; j < attrs.getLength(); j++){
                    //通过item(index)方法获取book节点的某一个属性
                    Node attr = attrs.item(j);
                    //获取属性名和属性值
                    System.out.println("属性名：" + attr.getNodeName() + "， 属性值：" + attr.getNodeValue());
                    book.setCategory(attr.getNodeValue());

                }


                /*
                //如果book节点有且只有一个category属性，可以通过将book节点进行强制类型转换，转换成Element类型。
                Element bookElement = (Element) booksList.item(i);
                //通过getAttribute("category")
                String attrVaule = bookElement.getAttribute("category");
                System.out.println("category的属性值为" + attrVaule);
                */

                //解析book节点的子节点
                NodeList bookchildNode = nodeBook.getChildNodes();
                //遍历bookchildNode获取每个节点的节点名和节点值
                System.out.println("第" + (i +1) + "本书共有" + bookchildNode.getLength() + "个子节点");

                for (int k = 0; k < bookchildNode.getLength(); k++){

                    //区分text类型的node和element类型的node
                    if (bookchildNode.item(k).getNodeType() == Node.ELEMENT_NODE){
                        //获取element类型的节点
                        System.out.println("\t第" + (k+1)+ "个节点的节点名：" + bookchildNode.item(k).getNodeName());
                        //获取element类型的节点值
                        System.out.println("\t节点值：" + bookchildNode.item(k).getFirstChild().getNodeValue());
                       // System.out.println("--节点值是：" + bookchildNode.item(k).getTextContent());

                        if(bookchildNode.item(k).getNodeName().equals("title")){
                            book.setTitle(bookchildNode.item(k).getFirstChild().getNodeValue());
                            Element nodeLang = (Element)  (bookchildNode.item(k));
                            String lang = nodeLang.getAttribute("lang");
                            book.setLanguage(lang);

                        }

                        if(bookchildNode.item(k).getNodeName().equals("author")){
                            book.setAuthor(bookchildNode.item(k).getFirstChild().getNodeValue());
                        }

                        if(bookchildNode.item(k).getNodeName().equals("year")){
                            book.setYear(bookchildNode.item(k).getFirstChild().getNodeValue());
                        }

                        if(bookchildNode.item(k).getNodeName().equals("price")){
                            book.setPrice(bookchildNode.item(k).getFirstChild().getNodeValue());
                        }

                    }
                }

                books.add(book);
                System.out.println("-----结束遍历第" + (i+1)+"本书的内容------");

            }
            System.out.println("------解析完毕！------");
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (ParserConfigurationException e){
            System.out.println(e.getMessage());
        }catch (SAXException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        //输出bookList中的book
        for(int m = 0; m < books.size(); m++){
            System.out.println("总共有" + books.size() + "本书");
            Books bookElemntTemp = (Books)books.get(m);
            System.out.println("第"+(m + 1) + "本书的分类：" + bookElemntTemp.getCategory() + ", 作者：" + bookElemntTemp.getAuthor() + "语言: " +bookElemntTemp.getLanguage());

        }
    }



}
