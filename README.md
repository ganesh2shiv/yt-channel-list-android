Youtube Channel List
====================

Android sample project that fetch (liked) videos from your youtube channel or any playlist.

Features
--------

* Use Youtube Data v3 API 
* Endless scroll RecyclerView with ProgressBar
* Youtube OAuth Integration with auto renewal token
* Fetch liked/watched videos from your youtube channel
* Handle configuration changes (orientation)
* Support landscape and portrait mode
* Fetch videos from any playlist

Screenshots
-----------

<img src="/art/01.png">
<img src="/art/02.png" hspace="70">
<img src="/art/03.png" vspace="50">
<img src="/art/04.png" hspace="70" vspace="50">
<img src="/art/05.png" hspace ="35">

Can I haz?
----------

Sure. Just follow the following steps after downloading this project.

1. Open [Google developers console](https://console.developers.google.com/project) and click on the "Create project" button

   ![Screenshot 1](/art/steps/01.png)

2. Enter the project name and click on the "Create" button

   ![Screenshot 2](/art/steps/02.png)

3. You would be redirected to the created project dashboard

4. Click on the "Enable and manage APIs" card on the screen.

   ![Screenshot 3](/art/steps/03.png)

5. Click on the Youtube Data v3 API and enable it.

6. Click on the "Go to Credentials" button

   ![Screenshot 4](/art/steps/04.png)

7. Click on the "What credentials do I need?" button

   ![Screenshot 5](/art/steps/05.png)

8. Enter the SHA1 signing-certificate fingerprint 

9. Enter the package name (app.vedicnerd.ytwua)

10. Click on the "Create client ID" button
   
   ![Screenshot 6](/art/steps/06.png)

11. Enter the product name and click on the "Continue" button

12. Click on the "Done" button

13. Click on "New Credentials" dropdown button

14. Select "API key" option in the dropdown

15. Click on the "Android key" button

16. Click on the "Create" button

   (You can restrict the API key usage to your app only by adding package name and fingerprint but that's optional)

17. Copy the API key and [paste it here](https://github.com/ganesh2shiv/yt-channel-list-android/blob/master/app/src/main/java/app/vedicnerd/ytwua/util/Constants.java#L11)

18. Done. Now you can run the app.

TODO
----

- [x] Fix fragment issue on config changes
- [ ] Fix duplicate items in list issue
- [ ] Include unit tests if possible
- [ ] Implement MVP architecture 

Credits
-------

Thanks to [mSobhy90](https://github.com/mSobhy90) for [this gist](https://gist.github.com/mSobhy90/cf7fa98803a0d7716a4a).

License
-------

    Copyright 2015 Ganesh Mohan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
