快速导入：

       添加依赖：compile 'com.zzb:zzb:1.4.2'

	   项目常用的一些东西：
	   一、引导页使用：
	   <com.zzb.zzblibrary.guide.GuideView
               android:id="@+id/guideViews"
               android:layout_width="match_parent"
               android:layout_height="match_parent" />

                            //引导页滑动监听
                           guideViews.setOnScrollChangedListerer(new GuideView.OnScrollChangedListerer() {
                               @Override
                               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                               }

                               @Override
                               public void onPageSelected(int position) {

                               }

                               @Override
                               public void onPageScrollStateChanged(int state) {

                               }
                           });

                            //设置引导页资源，可以改变滑动时候的颜色，具体看项目
                           guideViews.setData(getSupportFragmentManager(), resource, R.drawable.ic_point_select, R.drawable.ic_point_unselect, btnTiyan, GuideView.pointDefaultHeight);

         二、菜单页的使用
         <com.zzb.zzblibrary.base.view.BaseViewPagerMenuView
                 android:id="@+id/viewPagerMenuView"
                 android:layout_width="match_parent"
                 android:layout_height="match_parent"/>


                 fragmentManager = getSupportFragmentManager();
                         final FirstFragment firstFragment = new FirstFragment();
                         final SecondFragment secondFragment = new SecondFragment();
                         final SecondsFragment secondFragment2 = new SecondsFragment();
                         final MainFragment mainFragment = new MainFragment();

                         MenuBeans menuBeans = new MenuBeans();
                         menuBeans.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
                         menuBeans.setText("哈哈");
                         menuBeans.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
                         menuBeans.setTextColor(getResources().getColor(R.color.textcolor));
                         menuBeans.setFragment(secondFragment);

                         MenuBeans menuBeans2 = new MenuBeans();
                         menuBeans2.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
                         menuBeans2.setText("啦啦");
                         menuBeans2.setTextColor(getResources().getColor(R.color.textcolor));
                         menuBeans2.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
                         menuBeans2.setFragment(secondFragment2);

                         MenuBeans menuBeans3 = new MenuBeans();
                         menuBeans3.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
                         menuBeans3.setText("GG");
                         menuBeans3.setTextColor(getResources().getColor(R.color.textcolor));
                         menuBeans3.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
                         menuBeans3.setFragment(mainFragment);

                         int unSetectColor = getResources().getColor(R.color.textcolor);
                         int setectColor = getResources().getColor(R.color.selectColor);

                         listmenus.add(menuBeans);
                         listmenus.add(menuBeans2);
                         listmenus.add(menuBeans3);
                         viewPagerMenuView.addMenu(listmenus, fragmentManager, 0, setectColor, unSetectColor, false, false, true);

          三、可刷新的RecycleView
          <com.zzb.zzblibrary.refreshview.RefreshRecycleView
                  android:id="@+id/refreshRecycleView"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent" />

                          //设置刷新接口，默认不能刷新
                          refreshRecycleView.setRefreshCall(this);

                          //设置是否可以上拉刷新
                          refreshRecycleView.setPullLoadEnable(true);
                          //设置是否可以下拉刷新
                          refreshRecycleView.setPullRefreshEnable(false);
                          //解决滑动冲突
                          refreshRecycleView.setMoveForHorizontal(true);

                          //设置分割线
                          refreshRecycleView.addItemDecoration(new BaseDividerItem(10, Color.RED));

                          ceHuaAdapter = new CeHuaAdapter(this);
                          linearLayoutManager = new LinearLayoutManager(this);
                          linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                          refreshRecycleView.setAdapter(ceHuaAdapter);
                          refreshRecycleView.setLayoutManager(linearLayoutManager);

                          for (int i = 0; i < 20; i++) {
                              list.add("1");
                          }
                          ceHuaAdapter.addData(list);
                          refreshRecycleView.showEmptyImage(ceHuaAdapter.getList());
          四、拍照选择图片
                //fragment上不一致，选择TakePhotoFragmentUtils，TakePhotoChildFragmentUtils即可
                private TakePhotoActivityUtils photoUtils;

                photoUtils = new TakePhotoActivityUtils(this);
                        photoUtils.setOnImageFileCallBack(this);

                          @OnClick(R.id.img_head)
                            public void onViewClicked() {
                                photoUtils.takePhoto();
                            }

                            @Override
                            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                super.onActivityResult(requestCode, resultCode, data);
                                //photoUtils.onActivityResultWithCut(requestCode, resultCode, data);
                                photoUtils.onActivityResult(requestCode, resultCode, data);
                            }



           五、选择图片或者视频

               private MediaSelectUtils mediaSelectUtils;

               mediaSelectUtils = new MediaSelectUtils();


                 mediaSelectUtils.setOnFileCallBack(new MediaSelectUtils.OnFileCallBack() {
                            @Override
                            public void OnFile(ArrayList<Media> list, List<File> files, List<String> paths) {
                                LogUtils.e("paths: "+paths.size());
                                ToastUtils.show(mContext,"paths: "+paths.size());
                            }
                        });


                         @OnClick(R.id.img_head)
                            public void onViewClicked() {
                                mediaSelectUtils.takeMediaListInChildFragment(this, PickerConfig.PICKER_IMAGE_VIDEO,10);
                            }


                            @Override
                            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                super.onActivityResult(requestCode, resultCode, data);
                                mediaSelectUtils.onActivityResult(requestCode, resultCode, data);
                            }

                            @Override
                            public void OnImageFile(File file, String imagePath) {
                            }

                            @Override
                            public void onImageFiles(List<File> files, List<String> imagePaths) {

                            }

           六、android6.0权限申请

                 private String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                 XPermission.getPermissions(this, permission, new OnPermissionsListener() {
                             @Override
                             public void missPermission(String[] permissions) {

                             }
                         });

           七、版本更新
               private VersionUtils versionUtils;
               versionUtils = new VersionUtils(this);
               versionUtils.getUpVersion("2.0.2", "描述", "http://192.168.0.46/debug.apk ", "debug.apk ", "下载apk的文件夹名字");

           八、很多很多常用的东西，有空再写




