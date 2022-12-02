package dev.jpvillegas.bbcnewsrss.data.repository

object RssFeedRepositoryData {
    const val ResponseChannel = """
        <?xml version="1.0" encoding="UTF-8"?>
        <?xml-stylesheet title="XSL_formatting" type="tex/xsl" href="/shared/bsp/xsl/rss/nolsol.xsl"?>t/xsl" href="/shared/bsp/xsl/rss/nolsol.xsl"?>
        <rss xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:content="http://purl.org/rss/1.0/modules/content/" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0" xmlns:media="http://search.yahoo.com/mrss/">
        <channel>
            <title><![CDATA[BBC News - Home]]></title>
            <description><![CDATA[BBC News - Home]]></description>
            <link>https://www.bbc.co.uk/news/</link>
            <image>
                <url>https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif</url>
                <title>BBC News - Home</title>
                <link>https://www.bbc.co.uk/news/</link>
            </image>
            <generator>RSS for Node</generator>
            <lastBuildDate>Fri, 02 Dec 2022 02:33:36 GMT</lastBuildDate>
            <copyright><![CDATA[Copyright: (C) British Broadcasting Corporation, see http://news.bbc.co.uk/2/hi/help/rss/4498287.stm for terms and conditions of reuse.]]></copyright>
            <language><![CDATA[en-gb]]></language>
            <ttl>15</ttl>
            <item>
                <title><![CDATA[Ukraine war: Zelensky aide reveals up to 13,000 war dead]]></title>
                <description><![CDATA[Western casualty estimates suggest about 100,000 Ukrainian soldiers have been killed or wounded.]]></description>
                <link>https://www.bbc.co.uk/news/world-europe-63829973?at_medium=RSS&amp;at_campaign=KARANGA</link>
                <guid isPermaLink="false">https://www.bbc.co.uk/news/world-europe-63829973</guid>
                <pubDate>Fri, 02 Dec 2022 00:20:03 GMT</pubDate>
            </item>
            <item>
                <title><![CDATA[Doctors raise safety fears at Birmingham hospitals]]></title>
                <description><![CDATA[Whistleblowers allege they were punished for raising concerns at one of England's worst performing trusts.]]></description>
                <link>https://www.bbc.co.uk/news/uk-england-63827648?at_medium=RSS&amp;at_campaign=KARANGA</link>
                <guid isPermaLink="false">https://www.bbc.co.uk/news/uk-england-63827648</guid>
                <pubDate>Thu, 01 Dec 2022 22:32:32 GMT</pubDate>
            </item>
            <item>
                <title><![CDATA[Labour hold City of Chester seat in Rishi Sunak's first electoral test]]></title>
                <description><![CDATA[The by-election in the City of Chester is the first since the prime minister took office.]]></description>
                <link>https://www.bbc.co.uk/news/uk-politics-63825130?at_medium=RSS&amp;at_campaign=KARANGA</link>
                <guid isPermaLink="false">https://www.bbc.co.uk/news/uk-politics-63825130</guid>
                <pubDate>Fri, 02 Dec 2022 02:33:26 GMT</pubDate>
            </item>
        </channel>
    </rss>
    """


}