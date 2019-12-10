package com.mythology.cloud.apollo.search;

/**
 * Different modes of search.
 *
 * @author gyli
 * @date 2019/12/10 19:03
 */
public enum ScoreMode {

    /**
     * Produced scorers will allow visiting all matches and get their score.
     */
    COMPLETE {
        @Override
        public boolean needsScores() {
            return true;
        }
    },

    /**
     * Produced scorers will allow visiting all matches but scores won't be
     * available.
     */
    COMPLETE_NO_SCORES {
        @Override
        public boolean needsScores() {
            return false;
        }
    },

    /**
     * Produced scorers will optionally allow skipping over non-competitive
     * hits using the {@link Scorer#setMinCompetitiveScore(float)} API.
     */
    TOP_SCORES {
        @Override
        public boolean needsScores() {
            return true;
        }
    };

    /**
     * Whether this {@link ScoreMode} needs to compute scores.
     */
    public abstract boolean needsScores();
}
