/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class work_tinax_midij_play_RtMidiDevice */

#ifndef _Included_work_tinax_midij_play_RtMidiDevice
#define _Included_work_tinax_midij_play_RtMidiDevice
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    setRtMidiOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_setRtMidiOut
  (JNIEnv *, jobject);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    destroyRtMidiOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_work_tinax_midij_play_RtMidiDevice_destroyRtMidiOut
  (JNIEnv *, jobject);

/*
 * Class:     work_tinax_midij_play_RtMidiDevice
 * Method:    getDeviceNames
 * Signature: ()[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_work_tinax_midij_play_RtMidiDevice_getDeviceNames
  (JNIEnv *, jobject);

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
#endif
