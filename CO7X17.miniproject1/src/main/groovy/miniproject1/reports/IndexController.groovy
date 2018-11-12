package miniproject1.reports;

import com.mongodb.DBCollection
import java.util.List
import miniproject1.mongoDb.MongoDB
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	def mongo = new MongoDB()
	
	@RequestMapping
	public String index(Model model) {
		def connectionStatus = 'PENDING'
		if (mongo.connectionOk()) {
			connectionStatus = 'CONNECTED'
		}
		
		model.addAttribute('connectionStatus', connectionStatus)
		return "main"
		
	}
	

	@RequestMapping(value="followers", method=RequestMethod.GET)
    public String followers(Model model) {
		def followedFollowers = []
		
		// tag::exercise[]
		
		// Exercise 2
		//def regexp = '^.*(\bLeicester\b).*$'
		//def regexp1 = '^.*(\bGames\b).*$'
		DBCollection follower = mongo.db.getCollection("followers")
		follower.createIndex({location:"text"}, {text1:"Games"})
		followedFollowers = follower.find({$text:{$search:"Leicester"}}, {$text1:{$search:"Games"}})
		
		//followedFollowers = follower.find(location : {$regex:regexp}, text : {$regex:regexp1})
	
		//end::exercise[]
		
		model.addAttribute("followers", followedFollowers);
	    	return "followers";
    }

	
	@RequestMapping(value="friends",method=RequestMethod.GET)
	public String friends(Model model) {
		List<FriendDto> friends = new ArrayList<FriendDto>()
		
		// tag::exercise[]
		DBCollection friend1 = mongo.db.getCollection("followers")
		
		friend1.users.each{ friend ->
			FriendDto dto = new FriendDto()
			dto.name=friend.name
			dto.description=friend.description
		}
		
		
		dto.noFavoriteTweets = 0
		for (t in friends)
		{
			dto.noFavoriteTweets += t.favorite_count
			if (t.favorite_count >= 5)
				dto.noPopularTweets++
		}
		
		dto.noTweets = userTweets.size()
		
		friends.add(dto)

		//end::exercise[]
		
		model.addAttribute("friends", friends);
		return "friends";
	}
	
}
