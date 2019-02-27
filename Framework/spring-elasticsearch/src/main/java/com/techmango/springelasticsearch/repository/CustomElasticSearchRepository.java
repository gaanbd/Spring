package com.techmango.springelasticsearch.repository;


import java.util.List;

import org.springframework.data.domain.Page;


public interface CustomElasticSearchRepository {

	/*Full Text Queries*/
	<T> Page<T> searchByMatchQuery(String fieldName, String searchParam, Class<T> domainClass);
	
	<T> Page<T> searchByMultiMatchQuery(String[] fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByMatchPhraseQuery(String fieldName, String searchParam, Class<T> domainClass);
	
	<T> Page<T> searchByMatchPhrasePrefixQuery(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByMatchAllQuery(Class<T> domainClass);

	<T> Page<T> searchByCommonTermsQuery(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByQueryString(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByRangeQuery(String fieldName, String searchParam, String greaterThanValue, String lesserThanValue, Class<T> domainClass);

	<T> Page<T> searchByTermQuery(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByTermsQuery(String fieldName, List<String> searchParam, Class<T> domainClass);

	<T> Page<T> searchByRegexpQuery(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByFuzzyQuery(String fieldName, String searchParam, Class<T> domainClass);

	<T> Page<T> searchByBoolQuery(String mustFieldName, String mustNotFields, String shouldFields, String filterFields,
			String mustSearchParam, String mustNotSearchParam, String shouldSearchParam, String filterSearchParam,
			Class<T> domainClass);

	<T> Page<T> searchByBoostingQuery(String promoteFieldName, String promoteSearchParam, String demoteFieldName,
			String demoteSearchParam, Class<T> domainClass);

	<T> Page<T> searchByWildCardQuery(String fieldName, String searchParam, Class<T> domainClass);

}
