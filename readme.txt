按照手画的图，有四个界面。
用户登录界面、数据查询界面、数据详情界面、图表显示界面
从左到右分别为图一，图二，图三，图四。右下角第五个图是和图四是一个功能的两种做法，图四是模态对话框，图五是跳转到新的界面（第二种展现图表的方式）

用户登录界面：（图一）输入用户名和密码，到本地数据库的表中进行信息比对，比对正确即可登录并跳转到数据查询界面(图二)

数据查询界面：两种查询条件，按批号（主键）查询 和 按时间查询，如果是按时间查询，就是输入一个时间段，下方显示界面筛选出符合该时间段的记录，点击某条记录后跳转到批号详情界面（图三）；如果是按批号查询，那就是在最上方批号栏通过下拉菜单选择某个批号名，下方显示区域直接显示该批号对应的记录，点击后进入详情界面（图三）

数据详情界面：图三即展示某条记录的所有字段名和值（两栏：左边名字，右边值，大概20个字段），首行批号“锁定”（在滑动过程中始终位于首行，非必要功能，如果API提供此功能就加上去），末行是备注字段，“锁定”在底行。右下角是固定按钮，一个报警记录，一个温度曲线。温度曲线按钮点击后弹出（图四）

图表显示界面：图四或者图五按照方便程度来开发，弹出模态界面或者切换到新界面（横屏），用于显示一个温度-时间曲线图，数据我会写在数据表里。
关于这个界面，我想JAVA图形库是否提供类似的控件API？还是需要做个web窗口，加入CSS，js元素。我不太清楚。做的时候需要讨论一下。

我感觉我只能先完成这些功能了，国林！你开始做遇到问题跟我说，你要数据提前跟我说一下。我有些数据是压制成数组的，有些是目前没做实验没有的。所以我要重新搞一份数据给你。

这个本来是老师看我来不及了，帮我找了一个本科生做的。但是前几天把任务跟他描述的时候，他说要考研了，估计也无心帮我完成了。我大概4月20号答辩，这段时间在赶论文。我不急着要，你有空的时候帮我好了。有任何问题直接微信或者电话都可以。