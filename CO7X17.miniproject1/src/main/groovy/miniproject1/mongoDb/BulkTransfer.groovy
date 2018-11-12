package miniproject1.mongoDb

import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import groovy.json.JsonSlurper

class BulkTransfer {
	static void main(String... args) {

		MongoDB mongo = new MongoDB()
		JsonSlurper slurper = new JsonSlurper()
		def friends = slurper.parseText(new File('./friends.json').text)
		def followers = slurper.parseText(new File('./followers.json').text)
		def tweets = slurper.parseText(new File('./tweets.json').text)
		
		mongo.initDb()
		
		// tag::exercise[]
		
		// TODO Exercise 1
		
		DBCollection coll1 = mongo.db.getCollection("friends")
		DBCollection coll2 = mongo.db.getCollection("followers")
		BasicDBObject doc = new BasicDBObject()
		BasicDBObject DocTweet = new BasicDBObject()
		
		friends.users.each{ friend ->
			doc.put("id_str", friend.id_str)
			doc.put("name", friend.name)
			doc.put("description", friend.description)
			doc.put("favourites_count", friend.favourites_count)
			doc.put("followers_count", friend.followers_count)
			doc.put("friends_count", friend.friends_count)
			doc.put("location", friend.location)
			doc.put("screen_name", friend.screen_name)
			doc.put("url", friend.url)
			doc.put("created_at", friend.created_at)
			
			tweets.each { tweet ->
				if(friend.status.id_str == tweet.id_str)
				{
					DocTweet.put("id_str", tweet.id_str)
					DocTweet.put("created_at", tweet.created_at)
					DocTweet.put("entities", tweet.entities)
					DocTweet.put("favorite_count", tweet.favorite_count)
					DocTweet.put("favorited", tweet.favorited)
					DocTweet.put("user_id_str",, tweet.user.id_str)
					DocTweet.put("in_reply_to_screen_name", tweet.in_reply_to_screen_name)
					DocTweet.put("in_reply_to_status_id", tweet.in_reply_to_status_id)
					DocTweet.put("in_reply_to_user_id", tweet.in_reply_to_user_id)
					DocTweet.put("retweet_count", tweet.retweet_count)
					DocTweet.put("retweeted", tweet.retweeted)
					DocTweet.put("text", tweet.text)
					DocTweet.put("source", tweet.source)
					DocTweet.put("lang", tweet.lang)
					
					doc.put("Tweet_List", DocTweet)
					
					coll1.insert(new BasicDBObject(doc))
				}
			}
		}
		println "Data Successfully added in the Friends Collection!"
		followers.users.each{ follower ->
			doc.put("id_str", follower.id_str)
			doc.put("name", follower.name)
			doc.put("description", follower.description)
			doc.put("favourites_count", follower.favourites_count)
			doc.put("followers_count", follower.followers_count)
			doc.put("friends_count", follower.friends_count)
			doc.put("location", follower.location)
			doc.put("screen_name", follower.screen_name)
			doc.put("url", follower.url)
			doc.put("created_at", follower.created_at)
			
			tweets.each { tweet ->
				if(follower.status.id_str == tweet.id_str)
				{
					DocTweet.put("id_str", tweet.id_str)
					DocTweet.put("created_at", tweet.created_at)
					DocTweet.put("entities", tweet.entities)
					DocTweet.put("favorite_count", tweet.favorite_count)
					DocTweet.put("favorited", tweet.favorited)
					DocTweet.put("user_id_str",, tweet.user.id_str)
					DocTweet.put("in_reply_to_screen_name", tweet.in_reply_to_screen_name)
					DocTweet.put("in_reply_to_status_id", tweet.in_reply_to_status_id)
					DocTweet.put("in_reply_to_user_id", tweet.in_reply_to_user_id)
					DocTweet.put("retweet_count", tweet.retweet_count)
					DocTweet.put("retweeted", tweet.retweeted)
					DocTweet.put("text", tweet.text)
					DocTweet.put("source", tweet.source)
					DocTweet.put("lang", tweet.lang)
		
					doc.put("Tweet_List", DocTweet)
		
					coll2.insert(new BasicDBObject(doc))
				}
			}
		}
		println "Data Successfully added in the Follower Collection!"
				
		
		//end::exercise[]
	}
}

