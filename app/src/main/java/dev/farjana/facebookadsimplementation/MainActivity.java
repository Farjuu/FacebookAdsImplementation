package dev.farjana.facebookadsimplementation;

import static android.content.ContentValues.TAG;
import static com.facebook.ads.CacheFlag.ALL;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;


public class MainActivity extends AppCompatActivity implements AdListener {

    private AdView adView;
    private InterstitialAd interstitialAd;

    private NativeAdLayout nativeAdLayout;
    private LinearLayout NativeadView;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call this method for facebook banner ads
        loadBannerAds();
        loadIntertitialAd();
        loadNativeAd();

    }


    private void loadNativeAd() {

        nativeAd = new NativeAd(this, "IMG_16_9_APP_INSTALL#1114489809367699_1114651992684814");

        NativeAdListener nativeAdListener =new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
            // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
            // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

            // Request an ad
        nativeAd.loadAd();
    }

    private void inflateAd(NativeAd nativeAd) {



        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view. The layout referenced should be the one you created in the last step.
        NativeadView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(NativeadView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
       // AdIconView nativeAdIcon = NativeadView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = NativeadView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = NativeadView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = NativeadView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = NativeadView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = NativeadView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = NativeadView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        /*List[] clickableViews = new ArrayList[];
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);*/

// Register the Title and CTA button to listen for clicks.,
//                nativeAdIcon
//                ,clickableViews
        nativeAd.registerViewForInteraction(
                NativeadView,
                nativeAdMedia
        );
    }

    private void loadIntertitialAd() {
        // Instantiate an InterstitialAd object.

        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#1114489809367699_1114651366018210");

        // Set listeners for the Interstitial Ad

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "Fb failed :: " + adError.toString());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: ");
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };


        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .withCacheFlags(ALL)
                .build());


    }

    private void loadBannerAds() {

        adView = new AdView(this, "IMG_16_9_APP_INSTALL#1114489809367699_1114650822684931", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }

        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();

    }

    @Override
    public void onError(Ad ad, AdError adError) {

    }

    @Override
    public void onAdLoaded(Ad ad) {

    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }

}