package org.yearup.data;


import org.yearup.models.Profile;

import java.util.List;

public interface ProfileDao
{
    List<Profile> getAllProfiles();
    Profile getByUserId(int userId);
    Profile create(Profile profile);
    void update(Profile profile);
}
