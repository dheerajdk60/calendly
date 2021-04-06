/*
package com.mountblue.app.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class TagSpecification {

    public static Specification<Tag> getCommonTags(Tag tag)
    {
        return ((root,criteriaQuery,criteriaBuilder)->{
            return  criteriaBuilder.equal(root.join("posts", JoinType.INNER).get("tags"), tag.getName());
        });
    }
}
*/
