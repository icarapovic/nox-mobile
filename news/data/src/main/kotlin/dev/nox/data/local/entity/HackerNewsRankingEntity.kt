package dev.nox.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index

/**
 * Position of a [HackerNewsStoryEntity] within a news source list
 * (e.g. rank 3 in HN_TOP). A story can be ranked in multiple sources
 * */
@Entity(
    tableName = "hn_ranking",
    primaryKeys = ["news_source", "rank"],
    foreignKeys = [
        ForeignKey(
            entity = HackerNewsStoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["story_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("story_id")]
)
data class HackerNewsRankingEntity(
    @ColumnInfo(name = "news_source")
    val newsSource: String,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "story_id")
    val storyId: Long
)
