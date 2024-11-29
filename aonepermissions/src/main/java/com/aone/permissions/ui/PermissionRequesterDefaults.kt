package com.aone.permissions.ui

object PermissionRequesterDefaults {


    private const val RATIONAL_TITLE = "Required permission"
    private const val RATIONAL_PERMISSION_DECLINED_TITLE = "Required permission"
    private const val RATIONAL_DESCRIPTION = "This app needs to access this permission to use this feature."
    private const val RATIONAL_PERMISSION_DECLINED_DESCRIPTION =
        "It seems you permanently declined this permission. You can go to the app settings to grant it."

    fun permissionRational(
        title: String = RATIONAL_TITLE,
        permanentlyDeclinedTitle: String = RATIONAL_PERMISSION_DECLINED_TITLE,
        description: String = RATIONAL_DESCRIPTION,
        permanentlyDeclinedDescription: String = RATIONAL_PERMISSION_DECLINED_DESCRIPTION
    ): PermissionRationalProvider = object : PermissionRationalProvider {
        override fun getTitle(isPermanentlyDeclined: Boolean): String {
            return if (isPermanentlyDeclined) {
                permanentlyDeclinedTitle
            } else {
                title
            }
        }

        override fun getDescription(isPermanentlyDeclined: Boolean): String {
            return if (isPermanentlyDeclined) {
                permanentlyDeclinedDescription
            } else {
                description
            }
        }
    }


}