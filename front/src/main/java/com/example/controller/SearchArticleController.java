package com.example.controller;

import com.example.service.elasticsearch.SearchArticleService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujx on 16/12/23.
 */
@RestController
@RequestMapping(value="/search/article")
public class SearchArticleController {

    @Autowired
    private SearchArticleService searchArticleService;


    @RequestMapping(value = "/init")
    public String init() {
        this.searchArticleService.init();
        return "init success！";
    }

    /**
     * findAll
     * @param request
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> findList(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", this.searchArticleService.findAll());
        return params;
    }

    /**
     * find
     * @param title
     * @return
     */
    @RequestMapping(value = "/{title}/{page}", method = RequestMethod.GET)
    public Map<String, Object> search(@PathVariable String title,@PathVariable String page) {
        // 构建查询条件
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(title);
        Map<String, Object> params = new HashMap<String, Object>();
        int pageno = Integer.valueOf(page);
        if(pageno <=0){
            pageno = 0;
        }
        PageRequest pageRequest = new PageRequest(Integer.valueOf(page),10);
        params.put("items", this.searchArticleService.search(queryBuilder,pageRequest));
        return params;
    }
}
