package com.dpain.DiscordBot.plugin.wiki;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiFinder {
  private final static Logger logger = LoggerFactory.getLogger(WikiFinder.class);

  // Max message length is 2000. So charLimit must be at max: 1996
  public static int charLimit = 800;

  public static String search(String param) throws IOException {
    String parseLink = "";

    // Only works for Korean and English
    if (param.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
      parseLink = "https://ko.wikipedia.org/wiki/" + param.replace(" ", "_");
    } else {
      parseLink = "https://en.wikipedia.org/wiki/" + param.replace(" ", "_");
    }

    String result = "";
    Document doc = Jsoup.connect(parseLink).get();
    Element info = doc.select("div.mw-parser-output").first();
    Elements children = info.children();
    Elements sup = children.select("sup.reference");
    sup.remove();
    Elements paragraphs = new Elements();

    outerFor: for (Element e : children) {
      if (e.tagName().equals("p") || e.tagName().equals("ul")) {
        if (e.html().length() <= 0) {
          break outerFor;
        } else {
          paragraphs.add(e);
        }
      }
    }

    result = paragraphs.text();
    if (result.length() > charLimit) {
      result = result.substring(0, charLimit) + " ...";
    }

    return result;
  }
}
