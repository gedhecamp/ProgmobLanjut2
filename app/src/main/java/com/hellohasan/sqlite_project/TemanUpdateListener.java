package com.hellohasan.sqlite_project;

import com.hellohasan.sqlite_project.entity.Teman;

public interface TemanUpdateListener {
    void onTemanInfoUpdated(Teman teman, int position);
}
