package dv.serg.lentanews.pojo

import io.realm.RealmObject

open class SourceItem(var sourceTitle: String,
                      var sourceDesc: String,
                      var sourceName: String,
                      var sourceThumbnailDescriptor: String) : RealmObject() {
    constructor() : this("", "", "", "")
}

fun getPredefinedSources(): List<SourceItem> {
    return listOf(
            SourceItem(
                    sourceTitle = "RBC",
                    sourceDesc = """Главные новости политики, экономики и бизнеса, комментарии аналитиков, финансовые данные с российских и мировых биржевых систем на сайте rbc.ru.""",
                    sourceName = "rbc",
                    sourceThumbnailDescriptor = "rbc_thumb"
            ),

            SourceItem(
                    sourceTitle = "RT",
                    sourceDesc = """Актуальная картина дня на RT: круглосуточное ежедневное обновление новостей политики, бизнеса, финансов, спорта, науки, культуры. Онлайн-репортажи с места событий. Комментарии экспертов, актуальные интервью, фото и видео репортажи.""",
                    sourceName = "rt",
                    sourceThumbnailDescriptor = "rt_thumb"
            ),

            SourceItem(
                    sourceTitle = "Reddit",
                    sourceDesc = """Reddit is an entertainment, social news networking service, and news website. Reddit's registered community members can submit content, such as text posts or direct links.""",
                    sourceName = "reddit-r-all",
                    sourceThumbnailDescriptor = "reddit_r_all_thumb"
            ),


            SourceItem(
                    sourceTitle = "Reuters",
                    sourceDesc = """Reuters.com brings you the latest news from around the world, covering breaking news in business, politics, entertainment, technology, video and pictures.""",
                    sourceName = "reuters",
                    sourceThumbnailDescriptor = "reuters_thumb"
            ),

            SourceItem(
                    sourceTitle = "Time",
                    sourceDesc = """Breaking news and analysis from TIME.com. Politics, world news, photos, video, tech reviews, health, science and entertainment news.""",
                    sourceName = "time",
                    sourceThumbnailDescriptor = "time_thumb"
            ),
            SourceItem(
                    sourceTitle = "Marca",
                    sourceDesc = """La mejor información deportiva en castellano actualizada minuto a minuto en noticias, vídeos, fotos, retransmisiones y resultados en directo.""",
                    sourceName = "marca",
                    sourceThumbnailDescriptor = "marca_thumb"
            ),
            SourceItem(
                    sourceTitle = "Polygon",
                    sourceDesc = """Polygon is a gaming website in partnership with Vox Media. Our culture focused site covers games, their creators, the fans, trending stories and entertainment news.""",
                    sourceName = "polygon",
                    sourceThumbnailDescriptor = "polygon_thumb"
            ),
            SourceItem(
                    sourceTitle = "Recode",
                    sourceDesc = """Get the latest independent tech news, reviews and analysis from Recode with the most informed and respected journalists in technology and media""",
                    sourceName = "recode",
                    sourceThumbnailDescriptor = "recode_thumb"
            ),
            SourceItem(
                    sourceTitle = "TalkSport",
                    sourceDesc = """Tune in to the world's biggest sports radio station - Live Premier League football coverage, breaking sports news, transfer rumours &amp; exclusive interviews""",
                    sourceName = "talksport",
                    sourceThumbnailDescriptor = "talksport_thumb"
            ),
            SourceItem(
                    sourceTitle = "The Economist",
                    sourceDesc = """The Economist offers authoritative insight and opinion on international news, politics, business, finance, science, technology and the connections between them""",
                    sourceName = "the-economist",
                    sourceThumbnailDescriptor = "the_economist_thumb"
            ),
            SourceItem(
                    sourceTitle = "The Lad Bible",
                    sourceDesc = """The LAD Bible is one of the largest community for guys aged 16-30 in the world. Send us your funniest pictures and videos!""",
                    sourceName = "time",
                    sourceThumbnailDescriptor = "the_lad_bible_thumb"
            ),
            SourceItem(
                    sourceTitle = "Wired",
                    sourceDesc = """Wired is a monthly American magazine, published in print and online editions, that focuses on how emerging technologies affect culture, the economy, and politics.""",
                    sourceName = "wired",
                    sourceThumbnailDescriptor = "wired_thumb"
            ),
            SourceItem(
                    sourceTitle = "NHL News",
                    sourceDesc = """The most up-to-date breaking hockey news from the official source including interviews, rumors, statistics and schedules.""",
                    sourceName = "nhl-news",
                    sourceThumbnailDescriptor = "nhl_news_thumb"
            ),
            SourceItem(
                    sourceTitle = "Independent",
                    sourceDesc = """National morning quality (tabloid) includes free online access to news and supplements. Insight by Robert Fisk and various other columnists.""",
                    sourceName = "independent",
                    sourceThumbnailDescriptor = "independent_thumb"
            ),

            SourceItem(
                    sourceTitle = "CNN",
                    sourceDesc = """View the latest news and breaking news today for U.S., world, weather, entertainment, politics and health at CNN.""",
                    sourceName = "cnn",
                    sourceThumbnailDescriptor = "cnn_thumb"
            )
    )
}