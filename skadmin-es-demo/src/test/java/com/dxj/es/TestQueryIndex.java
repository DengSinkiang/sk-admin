package com.dxj.es;

import com.dxj.es.pojo.Item;
import com.dxj.es.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-07-16 22:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
public class TestQueryIndex {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testQueryAll() {

        Iterable<Item> items = itemRepository.findAll();
        for (Item item : items) {
            System.out.println("item = " + item);
        }
    }

    @Test
    public void testBetweenQuery() {

        itemRepository.findByPriceBetween(2000, 3000).forEach(item -> System.out.println("item = " + item));

        List<Item> itemList = itemRepository.findByPriceBetween(2000, 3000);
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }
    }

    @Test
    public void testQuerySelf() {

        //查询条件的构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        //执行查询
        Page<Item> result = this.itemRepository.search(queryBuilder.build());

        long total = result.getTotalElements();
        System.out.println("总共查询到：" + total);
        result.getContent().forEach(item -> System.out.println("item = " + item));
    }


    @Test
    public void testQuerySelfPage() {

        //查询条件的构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        queryBuilder.withPageable(PageRequest.of(0, 1));
        //执行查询
        Page<Item> result = this.itemRepository.search(queryBuilder.build());

        long total = result.getTotalElements();

        long totalPage = result.getTotalPages();
        System.out.println("总共查询到：" + total);
        System.out.println("totalPage = " + totalPage);
        result.getContent().forEach(item -> System.out.println("item = " + item));
    }

    @Test
    public void testQuerySelfSort() {

        //查询条件的构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        //执行查询
        Page<Item> result = this.itemRepository.search(queryBuilder.build());

        long total = result.getTotalElements();

        System.out.println("总共查询到：" + total);
        result.getContent().forEach(item -> System.out.println("item = " + item));
    }


    @Test
    public void testQuerySelfAggs() {

        //查询条件的构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //排除所有的字段查询，
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        //添加聚合条件
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"));

        //执行查询，把查询结果直接转为聚合page
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());

        //从所有的聚合中获取对应名称的聚合
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");


        //从聚合的结果中获取所有的桶信息
        List<StringTerms.Bucket> buckets = agg.getBuckets();

        for (StringTerms.Bucket bucket : buckets) {
            String brand = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();

            System.out.println("brand = " + brand + " 总数：" + docCount);
        }
    }


    @Test
    public void testQuerySelfSubAggs() {

        //查询条件的构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //排除所有的字段查询，
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        //添加聚合条件
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand")
                .subAggregation(AggregationBuilders.avg("avg_price").field("price")));

        //执行查询，把查询结果直接转为聚合page
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());

        //从所有的聚合中获取对应名称的聚合
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");


        //从聚合的结果中获取所有的桶信息
        List<StringTerms.Bucket> buckets = agg.getBuckets();

        for (StringTerms.Bucket bucket : buckets) {
            String brand = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();

            //取得内部聚合
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("avg_price");

            System.out.println("brand = " + brand + " 总数：" + docCount + " 平均价格：" + avg.getValue());
        }
    }

}


