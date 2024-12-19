package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile getProfileByUserId(int userId);

    Profile create(Profile profile);

    Profile update(Profile profile);
}
