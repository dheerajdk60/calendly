/*
package com.mountblue.app.specification;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class PostSpecification {

	public static Specification<Post> titleContains(String search) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			
			return  criteriaBuilder.like(root.get("title"), "%"+search+"%");
		});
	}

	
	
	public static Specification<Post> authorContains(String search) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			
			return  criteriaBuilder.like(root.get("author"), "%"+search+"%");
		});
	}
	public static Specification<Post> excerptContains(String search) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			
			return  criteriaBuilder.like(root.get("excerpt"), "%"+search+"%");
		});
	}
	public static Specification<Post> contentContains(String search) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			
			return  criteriaBuilder.like(root.get("content"), "%"+search+"%");
		});
	}
	
	
	public static Specification<Post> tagContains(String search) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			return  criteriaBuilder.equal(root.join("tags", JoinType.INNER).get("name"), search);
		});
	}
	
	
	public static Specification<Post> anyMatch(String search) {
		return contentContains(search).or(excerptContains(search)).or(titleContains(search)).or(authorContains(search)).or(tagContains(search));
	}
	
	
	public static Specification<Post> between(String from,String to) {
		if(from.length()==0)
			from="0000-00-00";
		if(to.length()==0)
			to="3000-00-00";
		final String finFrom=from+"T00:00:00";
		final String finTo=to+"T00:00:00";
		return ((root,criteriaQuery,criteriaBuilder)->{
			return  criteriaBuilder.between(root.get("publishedAt"), LocalDateTime.parse(finFrom),LocalDateTime.parse(finTo));
		});
	}
	
	
	public static Specification<Post> before(String to) {
		if(to.length()==0)
			to="3000-01-01";
		final String finTo=to+"T00:00:00";
		return ((root,criteriaQuery,criteriaBuilder)->{
			return  criteriaBuilder.lessThanOrEqualTo(root.get("publishedAt"),LocalDateTime.parse(finTo).plusDays(1));
		});
	}
	
	
	public static Specification<Post> after(String from) {
		if(from.length()==0)
			from="0000-01-01";
		final String finFrom=from+"T00:00:00";
		return ((root,criteriaQuery,criteriaBuilder)->{
			return  criteriaBuilder.greaterThanOrEqualTo(root.get("publishedAt"),LocalDateTime.parse(finFrom));
		});
	}
	
	
	public static Specification<Post> isAuthorPresent(List<String> selectedAuthors) {
		
		Specification<Post> specification=containsAuthor(selectedAuthors.get(0));
		for(int i=1;i<selectedAuthors.size();i++)
		{
			specification=specification.or(containsAuthor(selectedAuthors.get(i)));
		}
		return specification;
	}
	
	
	
	public static Specification<Post> containsAuthor(String authorName) {
		return ((root,criteriaQuery,criteriaBuilder)->{
			return  criteriaBuilder.equal(root.get("author"),authorName);
		});
	}

	public static Specification<Post> isTagPresent(List<String> tags) {
		Specification<Post> specification=tagContains(tags.get(0));
		for(int i=1;i<tags.size();i++)
		{
			specification=specification.or(tagContains(tags.get(i)));
		}
		return specification;
	}
}
*/
