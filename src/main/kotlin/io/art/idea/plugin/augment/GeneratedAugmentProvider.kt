package io.art.idea.plugin.augment

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.augment.PsiAugmentProvider
import com.intellij.psi.impl.source.PsiExtensibleClass
import com.intellij.psi.util.CachedValuesManager.getCachedValue
import io.art.idea.plugin.cached.GeneratedCachedValueProvider

class GeneratedAugmentProvider : PsiAugmentProvider() {
    override fun <Psi : PsiElement> getAugments(element: PsiElement, type: Class<Psi>): List<Psi> {
        if (type != PsiClass::class.java && type != PsiField::class.java && type != PsiMethod::class.java || element !is PsiExtensibleClass) {
            return emptyList()
        }
        if (!element.isValid()) {
            return emptyList()
        }
        val psiClass = element as PsiClass
        if (psiClass.isAnnotationType || psiClass.isInterface) {
            return emptyList()
        }
        val provider = GeneratedCachedValueProvider<Psi>(element.getProject(), psiClass)
        return when (type) {
            PsiField::class.java -> getCachedValue<List<Psi>>(element, provider) ?: emptyList()
            else -> emptyList()
        }
    }

}
