package me.bytebeats.algorithms.kt.design

import java.util.function.Predicate

class Twitter() {//355

    /** Initialize your data structure here. */
    private val following = mutableMapOf<Int, HashSet<Int>>()
    private val tweets = mutableListOf<Tweet>()

    /** Compose a new tweet. */
    fun postTweet(userId: Int, tweetId: Int) {
        tweets.add(Tweet(userId, tweetId))
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    fun getNewsFeed(userId: Int): List<Int> {
        val followeeIds = following[userId]
        val predicate = Predicate<Tweet> { it.userId == userId || followeeIds?.contains(it.userId) ?: false }
        return tweets.filter { predicate.test(it) }.map { it.tweetId }.reversed().take(10)
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    fun follow(followerId: Int, followeeId: Int) {
        var set = following[followerId]
        if (set == null) {
            set = HashSet()
            following[followerId] = set
        }
        set?.add(followeeId)
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    fun unfollow(followerId: Int, followeeId: Int) {
        following[followerId]?.remove(followeeId)
    }

    class Tweet(val userId: Int, val tweetId: Int)

}