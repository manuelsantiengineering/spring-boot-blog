package com.mesanti.blog.repositories;

import java.util.List;

import com.mesanti.blog.models.Blog;
import com.mesanti.blog.models.Comment;

public interface BlogRepositoryCustom {

	List<Comment> getAllComments(int from, int size);
	
	List<Comment> getCommentsForStatus(String status,int from, int size);
	
	int getCurrentChildSequence(String blogId, String parentCommentId);
	
	List<Blog> search(String searchTxt);
	
}