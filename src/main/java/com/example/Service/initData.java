package com.example.Service;

import com.example.Entites.Userr;

public interface initData {
      String initArticles();
      String initCategories();
      String initMarque();
      String InitUser();
      String initCommands();
      String initRating();
      Userr ConsultUserByName(String s);
      String initRoles();
}
