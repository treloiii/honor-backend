package Utils;

import Entities.Actions;
import Entities.News;
import Entities.Post;

public class CompilationObject {
    private News news;
    private Post post;
    private Actions rally;
    private Actions event;

    public CompilationObject(News news, Post post, Actions actions,Actions event) {
        this.news = news;
        this.post = post;
        this.rally = actions;
        this.event=event;
    }

    public void setRally(Actions rally) {
        this.rally = rally;
    }

    public void setEvent(Actions event) {
        this.event = event;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    public News getNews() {
        return news;
    }

    public Actions getRally() {
        return rally;
    }

    public Actions getEvent() {
        return event;
    }

    public Post getPost() {
        return post;
    }


}
