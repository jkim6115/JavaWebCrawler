package com.yhkim.javawebcrawler.wiki;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class WikiExample {

    public static void main(String[] args) throws IOException {
        String url = "https://ko.wikipedia.org/wiki/자바_(프로그래밍_언어)";
        Connection conn = Jsoup.connect(url);
        Document doc = conn.get();

        Element content = doc.getElementById("mw-content-text");
        Elements paras = content.select("p");
        Element firstPara = paras.get(1);

        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        for (Node node : iter) {
            if (node instanceof TextNode) {
                System.out.println(node);
            }
        }
    }

    // stack은 실행 순서를 보장한다.
    // java의 stack 클래스는 만들어진지 너무 오래되어 jcf와 일치하지 않는다.
    // deque는 양쪽에 끝이 있는 queue이다.
    // 이 메서드는 arraylist를 이용해 stack을 구현하였다.
    private static void iterativeDFS(Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node instanceof TextNode) {
                System.out.println(node);
            }

            List<Node> nodes = new ArrayList<>(node.childNodes());
            Collections.reverse(nodes);

            for (Node child: nodes) {
                stack.push(child);
            }
        }
    }

    // 전위 순회
    // 루트에서 시작하여 트리에 있는 모든 노드를 호출한다.
    // 만약 node가 textnode이면 출력한다.
    // 재귀적 호출 -> 호출 스택
    private static void recursiveDFS(Node node) {
        if (node instanceof TextNode) {
            System.out.println(node);
        }
        for (Node child: node.childNodes()) {
            recursiveDFS(child);
        }
    }

}
