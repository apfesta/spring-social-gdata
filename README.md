# Spring Social Gdata (Google Data API)

To check out the project and build from source, do the following:

    git clone --recursive git://github.com/apfesta/spring-social-gdata.git
    cd spring-social-instagram
    mvn install

##Why Gdata?
GData is Google's Data API for some of their non-Google+ services.  Google has been slowly migrating their individual APIs into Google+, but not all of them have transitioned yet (like Google Docs, Picasa Web Albums, etc), thus spring-social-google doesn't have these operators yet.  This library allows us to wrap the Spring Social OAuth2 factories around the older GoogleService APIs.

Since Gdata will eventually go away and be deprecated, I've decided not to reimplement that API, but instead to expose their Services with this framework.  For the documentation on the gdata-java-client components, see http://code.google.com/p/gdata-java-client.

##Dependencies

Spring Social GData depends on the GData Java Client libraries that are only available from Google.  See http://code.google.com/p/gdata-java-client/ to download those jars and upload them to your local maven repository.

