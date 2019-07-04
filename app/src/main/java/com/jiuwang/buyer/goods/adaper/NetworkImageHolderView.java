package com.jiuwang.buyer.goods.adaper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * 图片轮播适配器
 */
public class NetworkImageHolderView implements Holder<String> {
    private View rootview;
    private ImageView imageView;

    @Override
    public View createView(Context context) {
//        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.goods_item_head_img, null);
//        imageView = (ImageView) rootview.findViewById(R.id.iv);
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
         imageView = new ImageView(context);
         imageView.setScaleType(ImageView.ScaleType.CENTER);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//        layoutParams.height = layoutParams.WRAP_CONTENT;
//        layoutParams.width = layoutParams.WRAP_CONTENT;
//        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setImageURI(Uri.parse(data));
//        Glide.with(context).load(data).into(imageView);
        Glide.with(context)
//				.load(datas.get(position).getImgUrl())
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
