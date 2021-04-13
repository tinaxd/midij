#include "../../../include/work_tinax_midij_play_RtMidiDevice.h"
#include <RtMidi.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getDeviceNames
 * Signature: ()Ljava/util/List;
 */
JNIEXPORT jobject JNICALL Java_work_tinax_midij_play_RtMidiDevice_getDeviceNames
  (JNIEnv *env, jobject) {
  jclass listCls = env->FindClass("java/util/ArrayList");
  jmethodID listCtor = env->GetMethodID(listCls, "<init>", "()V");
  jobject listObj = env->NewObject(listCls, listCtor);
}

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    openDevice
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_openDevice__Ljava_lang_String_2
  (JNIEnv *, jobject, jstring);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    openDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_openDevice__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    closeDevice
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_closeDevice
  (JNIEnv *, jobject);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getCurrentPortName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_work_tinax_midij_play_RtMidiDevice_getCurrentPortName
  (JNIEnv *, jobject);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getRtMidiApiName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_work_tinax_midij_play_RtMidiDevice_getRtMidiApiName
  (JNIEnv *, jobject);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    sendMessage
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_sendMessage
  (JNIEnv *, jobject, jbyteArray);

#ifdef __cplusplus
}
#endif
