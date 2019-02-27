package com.techmango.springelasticsearch.repository.impl;


import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.boostingQuery;
import static org.elasticsearch.index.query.QueryBuilders.commonTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhrasePrefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.techmango.springelasticsearch.repository.CustomElasticSearchRepository;


public class CustomElasticSearchRepositoryImpl implements CustomElasticSearchRepository {

	private ElasticsearchTemplate elasticsearchTemplate;

	public CustomElasticSearchRepositoryImpl(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	/* standard query for performing full text queries */
	@Override
	public <T> Page<T> searchByMatchQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery(fieldName, searchParam)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/* The multi-field version of the match query */
	@Override
	public <T> Page<T> searchByMultiMatchQuery(String[] fieldNames, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(multiMatchQuery(searchParam).field(fieldNames[0]).field(fieldNames[1])).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * match_phrase query analyzes the text and creates a phrase query out of the
	 * analyzed text
	 */
	@Override
	public <T> Page<T> searchByMatchPhraseQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery(fieldName, searchParam))
				.build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * match_phrase_prefix is the same as match_phrase, except that it allows for
	 * prefix matches on the last term in the text
	 */
	@Override
	public <T> Page<T> searchByMatchPhrasePrefixQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchPhrasePrefixQuery(fieldName, searchParam).maxExpansions(10)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/* matches all documents */
	@Override
	public <T> Page<T> searchByMatchAllQuery(Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	@Override
	public <T> Page<T> searchByCommonTermsQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(commonTermsQuery(fieldName, searchParam))
				.build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/* search with i/p query string values in specific fields only */
	@Override
	public <T> Page<T> searchByQueryString(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery;
		if (StringUtils.isNotBlank(fieldName)) {
			searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(searchParam)).withFields(fieldName)
					.build();
		} else {
			searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(searchParam)).build();
		}
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents which contain the exact term specified in the field specified.
	 */
	@Override
	public <T> Page<T> searchByTermQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery(fieldName, searchParam)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents which contain any of the exact terms specified in the field
	 * specified.
	 */
	@Override
	public <T> Page<T> searchByTermsQuery(String fieldName, List<String> searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(termsQuery(fieldName, searchParam)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents where the field specified contains values (dates, numbers, or
	 * strings) in the range specified
	 */
	@Override
	public <T> Page<T> searchByRangeQuery(String fieldName, String searchParam, String greaterThanValue,
			String lesserThanValue, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(rangeQuery(fieldName).gte(greaterThanValue).lt(lesserThanValue)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents where the field specified contains terms which match the
	 * regular expression specified. increase
	 * "max_determinized_states":10000(default) value to allow more complex regexp
	 */
	@Override
	public <T> Page<T> searchByRegexpQuery(String fieldName, String regexParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(regexpQuery(fieldName, regexParam)
				.boost(1.2f))
				.build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * The default query for combining multiple leaf or compound query clauses, as
	 * must, should, must_not, or filter clauses
	 */
	@Override
	public <T> Page<T> searchByBoolQuery(String mustFieldName, String mustNotFields, String shouldFields,
			String filterFields, String mustSearchParam, String mustNotSearchParam, String shouldSearchParam,
			String filterSearchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(boolQuery()
				.must(termQuery(mustFieldName, mustSearchParam))
				.mustNot(termQuery(mustNotFields, mustNotSearchParam))
				.should(termQuery(shouldFields, shouldSearchParam))
				.filter(termQuery(filterFields, filterSearchParam)))
				.build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Return documents which match a positive query, but reduce the score of
	 * documents which also match a negative query
	 */
	@Override
	public <T> Page<T> searchByBoostingQuery(String promoteFieldName, String promoteSearchParam, String demoteFieldName, String demoteSearchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(boostingQuery(termQuery(promoteFieldName, promoteSearchParam), termQuery(demoteFieldName, demoteSearchParam))
						.negativeBoost(2.0f))
						.build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents where the field specified contains terms which match the
	 * pattern specified, where the pattern supports single character wildcards (?)
	 * and multi-character wildcards (*)
	 */
	@Override
	public <T> Page<T> searchByWildCardQuery(String fieldName, String searchParamWithWildCardPattern, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(wildcardQuery(fieldName, searchParamWithWildCardPattern)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

	/*
	 * Find documents where the field specified contains terms which are fuzzily
	 * similar to the specified term
	 */
	@Override
	public <T> Page<T> searchByFuzzyQuery(String fieldName, String searchParam, Class<T> domainClass) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(fuzzyQuery(fieldName, searchParam)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, domainClass);
	}

}
