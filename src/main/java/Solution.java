class Solution {
    public int strStr(String haystack, String needle) {
        char[] hayList = haystack.toCharArray();
        char[] needleList = needle.toCharArray();
        if(hayList.length == 0 && needleList.length ==0){
            return 0;
        }

        for(int i=0;i<hayList.length;i++){
            int j = i+ needleList.length -1;
            if(j >= hayList.length){
                return  -1;
            }
            boolean result = true;
            for(int k = i;k<=j;k++){
                if(hayList[k] != needleList[k-i]){
                    result =false;
                    break;
                }
            }
            if(result){
                return i;
            }
        }
        return 0;


    }
    public static void main(String[] args) {
        System.out.println(new Solution().strStr("mississippi","issip"));
    }
}