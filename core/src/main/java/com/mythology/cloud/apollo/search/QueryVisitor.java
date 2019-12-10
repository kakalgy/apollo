package com.mythology.cloud.apollo.search;

import com.mythology.cloud.apollo.index.Term;

import java.util.Arrays;
import java.util.Set;

/**
 * Allows recursion through a query tree
 *
 * @author gyli
 * @date 2019/12/10 14:55
 * @see Query#visit(QueryVisitor)
 */
public abstract class QueryVisitor {

    /**
     * Called by leaf queries that match on specific terms
     *
     * @param query the leaf query
     * @param terms the terms the query will match on
     */
    public void consumeTerms(Query query, Term... terms) {
    }

    // TODO it would be nice to have a way to consume 'classes' of Terms from
    // things like AutomatonQuery

    /**
     * Called by leaf queries that do not match on terms
     *
     * @param query the query
     */
    public void visitLeaf(Query query) {
    }

    /**
     * Whether or not terms from this field are of interest to the visitor
     * <p>
     * Implement this to avoid collecting terms from heavy queries such as {@link TermInSetQuery}
     * that are not running on fields of interest
     */
    public boolean acceptField(String field) {
        return true;
    }

    /**
     * Pulls a visitor instance for visiting child clauses of a query
     * <p>
     * The default implementation returns {@code this}, unless {@code occur} is equal
     * to {@link BooleanClause.Occur#MUST_NOT} in which case it returns
     * {@link #EMPTY_VISITOR}
     *
     * @param occur  the relationship between the parent and its children
     * @param parent the query visited
     */
    public QueryVisitor getSubVisitor(BooleanClause.Occur occur, Query parent) {
        if (occur == BooleanClause.Occur.MUST_NOT) {
            return EMPTY_VISITOR;
        }
        return this;
    }

    /**
     * Builds a {@code QueryVisitor} instance that collects all terms that may match a query
     *
     * @param termSet a {@code Set} to add collected terms to
     */
    public static QueryVisitor termCollector(Set<Term> termSet) {
        return new QueryVisitor() {
            @Override
            public void consumeTerms(Query query, Term... terms) {
                termSet.addAll(Arrays.asList(terms));
            }
        };
    }

    /**
     * A QueryVisitor implementation that does nothing
     */
    public static final QueryVisitor EMPTY_VISITOR = new QueryVisitor() {
    };

}

