package fi.metatavu.essote.palaute.api.test.functional.data

import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeReview
import java.time.OffsetDateTime

class TestReviewsData {

    companion object {

        /**
         * Lists mocked reviews
         *
         * @return Array of Reviews
         */
        fun listMockReviewsOne(): Array<BisnodeReview> {
            return arrayOf(
                BisnodeReview(
                    id =  0,
                    rating = 0,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  1,
                    rating = 1,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  2,
                    rating = 2,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  3,
                    rating = 3,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  4,
                    rating = 4,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  5,
                    rating = 5,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  6,
                    rating = 0,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  7,
                    rating = 1,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  8,
                    rating = 2,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  9,
                    rating = 3,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  10,
                    rating = 4,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  11,
                    rating = 5,
                    created = OffsetDateTime.now()
                )
            )
        }

        /**
         * Lists mocked reviews
         *
         * @return Array of Reviews
         */
        fun listMockReviewsTwo(): Array<BisnodeReview> {
            return arrayOf(
                BisnodeReview(
                    id =  0,
                    rating = 0,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  1,
                    rating = 1,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  2,
                    rating = 2,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  3,
                    rating = 3,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  4,
                    rating = 4,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  5,
                    rating = 5,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  6,
                    rating = 0,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  7,
                    rating = 1,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  8,
                    rating = 2,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  9,
                    rating = 3,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  10,
                    rating = 4,
                    created = OffsetDateTime.now()
                ),
                BisnodeReview(
                    id =  11,
                    rating = 5,
                    created = OffsetDateTime.now()
                )
            )
        }
    }
}