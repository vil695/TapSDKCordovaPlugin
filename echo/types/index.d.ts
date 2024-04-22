declare namespace VilEcho {
    /**
     * 自行判断版本号后，确定需要强更时调用
     * @example TapSDK.updateGame(()=>log('user canceled'));
     */
    function updateGame(onSuccess:Function): void;
}