package com.example.airhandsome.safelogin;

public class Calculate {

    public float variance(float arr[], int len){
        float ave = 0;
        for( int i = 0; i<len; i++){
            ave += arr[i];
        }
        ave /= len;

        float s = 0;
        for( int i = 0; i < len; i++){
            s += sqrs(arr[i] - ave, 2) / len;
        }
        return s;
    }
    float sqrs(float x, int n){
        float ans = 1;
        for(int i = 0; i < n; i++){
            ans *= x;
        }
        return ans;
    }

    float expect(float a[], float p[]){
        float ans = 0;
        float total = 0;
        for (int i = 0; i<p.length; i++){
            total += p[i];
        }
        for( int i = 0; i<p.length; i++){
            p[i] = p[i] / total;
        }
        for(int i = 0; i<a.length; i++){
            ans += a[i]*p[i];
        }
        return ans;
    }

    float avg(float a[], int len){
        if (len == 0 ) return 0;
        float ans = 0;
        for(int i=0; i<len; i++)
            ans += a[i];
        ans /= len;
    //    Math.round(ans);
        return  ans;
    }
}
