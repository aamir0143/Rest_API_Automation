package com.bridgelabz.spotify;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyApi {
	public String token;
	String user_id;
	String playlist_id;
	String tracks;
	@BeforeTest
	public void getToken() {
		token = "Bearer BQC5cgCpCFLRq6creLG2Gh6pXzofQXTwvgRJd5MLcp6GDbNjzOBX68gEr4qNl_ks0yIFdoCZq4UfRnEPafUovf0zCBXa8oZCgUmr34XC7a4VwLnGTisxJr4dLZxrUkMVu8zPyzJwQHNJ3e6eL5erjFBanpE6R9jt0CLIZpwlO4BEVzW6BiWRiZ1zBmc-H1pq7H5n-LXN1ykU8K-ShU9LW3Na--vfDOcmlBG3OJ2q-9neQvy5mCqDQfD63JILGbwXeH0VFGaCVTdBXU4BrtuwgA";
	}
	@BeforeTest
	public void getTracks() {
		tracks = "spotify:track:5doN1AbRIHIgpGMtRAjqRn";
	}
	@Test (priority = 0)
	public void testGet_CurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		user_id = response.path("id");
		System.out.print("User Id Is:"+user_id);
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 1)
	public void testGet_UsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/users/"+user_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 2)
	public void createPlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"Automated Playlist\",\r\n"
						+ "  \"description\": \"New playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.post("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		playlist_id = response.path("id");
		System.out.print("Playlist Id Is:"+playlist_id);
		response.then().assertThat().statusCode(201);
	}
	@Test (priority = 3)
	public void add_Items_to_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("uris", tracks)
				.when()
				.post("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	@Test (priority = 4)
	public void remove_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{ \"tracks\": [{ \"uri\": \"spotify:track:3W9EFWiTzexkdlCm2zHBqK\" }] }")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 5)
	public void get_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 6)
	public void get_Users_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 7)
	public void get_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 8)
	public void get_Current_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 9)
	public void change_Playlist_Details() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"Updated Playlist CQA_Automated\",\r\n"
						+ "  \"description\": \"Updated playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 10)
	public void search_For_Item() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("q", "kumar sanu")
				.queryParams("type", "track")
				.when()
				.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 11)
	public void update_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"range_start\": 0,\r\n"
						+ "  \"insert_before\": 3,\r\n"
						+ "  \"range_length\": 2\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/0uwANkZvbYlpJ3WCINajD2/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 12)
	public void get_Tracks_Audio_Analysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/4n5vaqluWQjCrKr4JLZIRh");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 13)
	public void get_Tracks_Audio_Features() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 14)
	public void get_Track_Audio_Features() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features/4n5vaqluWQjCrKr4JLZIRh");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 15)
	public void get_Several_Tracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks?ids=4n5vaqluWQjCrKr4JLZIRh,5doN1AbRIHIgpGMtRAjqRn");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 16)
	public void get_Track() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks/4n5vaqluWQjCrKr4JLZIRh");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 17)
	public void get_Playlist_CoverImage() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
}
