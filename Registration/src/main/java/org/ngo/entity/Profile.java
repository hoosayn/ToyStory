package org.ngo.entity;

import lombok.Data;

import java.net.URL;

@Data
public class Profile {
   private User user;

   public Profile(User user){
       this.user.setTitle( user.getTitle());
       this.user.setFirstName(user.getFirstName());
       this.user.setLastName(user.getLastName());
   }
}
