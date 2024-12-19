package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {

    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public Profile getProfile(Principal principal){
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User wasn't authenticated.");
        }
        String username = principal.getName();

        User user = userDao.getByUserName(username);
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found.");
        }

        Profile profile = profileDao.getProfileByUserId(user.getId());
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return profile;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping()
    public Profile updateProfile(@RequestBody Profile profile, Principal principal) {

        String username = principal.getName();

        int userId = userDao.getByUserName(username).getId();
        profile.setUserId(userId);

        return profileDao.update(profile);
    }
}
