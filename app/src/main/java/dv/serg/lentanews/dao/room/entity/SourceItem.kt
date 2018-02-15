package dv.serg.lentanews.dao.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "selected_sources")
data class SourceItem(
        @ColumnInfo(name = "source_title")
        val sourceTitle: String,
        @ColumnInfo(name = "source_desc")
        val sourceDesc: String,
        @ColumnInfo(name = "source_name")
        val sourceName: String,
        @ColumnInfo(name = "source_thumbnail")
        val sourceThumbnailDescriptor: String,
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = false)
        var id: Int = 0
)

fun getPredefinedSources(): List<SourceItem> {
    return listOf(
            SourceItem(
                    sourceTitle = "RBC",
                    sourceDesc = """Главные новости политики, экономики и бизнеса, комментарии аналитиков, финансовые данные с российских и мировых биржевых систем на сайте rbc.ru.""",
                    sourceName = "rbc",
                    sourceThumbnailDescriptor = "rbc_thumb",
                    id = 1
            ),

            SourceItem(
                    sourceTitle = "Lenta",
                    sourceDesc = """Новости, статьи, фотографии, видео. Семь дней в неделю, 24 часа в сутки.""",
                    sourceName = "lenta",
                    sourceThumbnailDescriptor = "lenta_touch",
                    id = 2
            ),

            SourceItem(

                    sourceTitle = "RT",
                    sourceDesc = """Актуальная картина дня на RT: круглосуточное ежедневное обновление новостей политики, бизнеса, финансов, спорта, науки, культуры. Онлайн-репортажи с места событий. Комментарии экспертов, актуальные интервью, фото и видео репортажи.""",
                    sourceName = "rt",
                    sourceThumbnailDescriptor = "rt_thumb",
                    id = 3
            ),


            SourceItem(
                    sourceTitle = "Reuters",
                    sourceDesc = """Reuters.com brings you the latest news from around the world, covering breaking news in business, politics, entertainment, technology, video and pictures.""",
                    sourceName = "reuters",
                    sourceThumbnailDescriptor = "reuters_thumb",
                    id = 4
            ),

            SourceItem(
                    sourceTitle = "Time",
                    sourceDesc = """Breaking news and analysis from TIME.com. Politics, world news, photos, video, tech reviews, health, science and entertainment news.""",
                    sourceName = "time",
                    sourceThumbnailDescriptor = "time_thumb",
                    id = 5
            ),
            SourceItem(
                    sourceTitle = "Marca",
                    sourceDesc = """La mejor información deportiva en castellano actualizada minuto a minuto en noticias, vídeos, fotos, retransmisiones y resultados en directo.""",
                    sourceName = "marca",
                    sourceThumbnailDescriptor = "marca_thumb",
                    id = 6
            ),
            SourceItem(
                    sourceTitle = "Polygon",
                    sourceDesc = """Polygon is a gaming website in partnership with Vox Media. Our culture focused site covers games, their creators, the fans, trending stories and entertainment news.""",
                    sourceName = "polygon",
                    sourceThumbnailDescriptor = "polygon_thumb",
                    id = 7
            ),
            SourceItem(
                    sourceTitle = "Recode",
                    sourceDesc = """Get the latest independent tech news, reviews and analysis from Recode with the most informed and respected journalists in technology and media""",
                    sourceName = "recode",
                    sourceThumbnailDescriptor = "recode_thumb",
                    id = 8
            ),
            SourceItem(
                    sourceTitle = "TalkSport",
                    sourceDesc = """Tune in to the world's biggest sports radio station - Live Premier League football coverage, breaking sports news, transfer rumours &amp; exclusive interviews""",
                    sourceName = "talksport",
                    sourceThumbnailDescriptor = "talksport_thumb",
                    id = 9
            ),
            SourceItem(
                    sourceTitle = "The Economist",
                    sourceDesc = """The Economist offers authoritative insight and opinion on international news, politics, business, finance, science, technology and the connections between them""",
                    sourceName = "the-economist",
                    sourceThumbnailDescriptor = "the_economist_thumb",
                    id = 10
            ),
            SourceItem(
                    sourceTitle = "The Lad Bible",
                    sourceDesc = """The LAD Bible is one of the largest community for guys aged 16-30 in the world. Send us your funniest pictures and videos!""",
                    sourceName = "time",
                    sourceThumbnailDescriptor = "the_lad_bible_thumb",
                    id = 11
            ),
            SourceItem(
                    sourceTitle = "Wired",
                    sourceDesc = """Wired is a monthly American magazine, published in print and online editions, that focuses on how emerging technologies affect culture, the economy, and politics.""",
                    sourceName = "wired",
                    sourceThumbnailDescriptor = "wired_thumb",
                    id = 12
            ),
            SourceItem(
                    sourceTitle = "NHL News",
                    sourceDesc = """The most up-to-date breaking hockey news from the official source including interviews, rumors, statistics and schedules.""",
                    sourceName = "nhl-news",
                    sourceThumbnailDescriptor = "nhl_news_thumb",
                    id = 13
            ),
            SourceItem(
                    sourceTitle = "Independent",
                    sourceDesc = """National morning quality (tabloid) includes free online access to news and supplements. Insight by Robert Fisk and various other columnists.""",
                    sourceName = "independent",
                    sourceThumbnailDescriptor = "independent_thumb",
                    id = 14
            ),

            SourceItem(
                    sourceTitle = "CNN",
                    sourceDesc = """View the latest news and breaking news today for U.S., world, weather, entertainment, politics and health at CNN.""",
                    sourceName = "cnn",
                    sourceThumbnailDescriptor = "cnn_thumb",
                    id = 15
            ),

            SourceItem(
                    sourceTitle = "Reddit",
                    sourceDesc = """Reddit is an entertainment, social news networking service, and news website. Reddit's registered community members can submit content, such as text posts or direct links.""",
                    sourceName = "reddit-r-all",
                    sourceThumbnailDescriptor = "reddit_r_all_thumb",
                    id = 16
            )
    )
}