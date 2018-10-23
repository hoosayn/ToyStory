package org.ngo.registration.entity;

import lombok.Data;

@Data
public class Profile {
   private User user;

   public Profile(User user){
       this.user.setTitle( user.getTitle());
       this.user.setFirstName(user.getFirstName());
       this.user.setLastName(user.getLastName());
   }
}
