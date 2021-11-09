#!/bin/bash
SCRIPTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null && pwd)"
PROJECT_ROOT=$(cd "$(dirname "${SCRIPTDIR}/../../../../../")" >/dev/null && pwd)
MODULE_ROOT=$(cd "$(dirname "${SCRIPTDIR}/../../../../")" >/dev/null && pwd)

CURRENT_VERSION_USED=$(fgrep  '<antlr.version>' "${PROJECT_ROOT}/pom.xml" | sed 's@^.*>\(.*\)<.*$@\1@g')

PATCHED_FILES=(
    "org/antlr/v4/runtime/ParserRuleContext.java"
    "org/antlr/v4/runtime/RuleContext.java"
    "org/antlr/v4/runtime/Recognizer.java"
    "org/antlr/v4/runtime/tree/RuleNode.java"
    "org/antlr/v4/runtime/tree/TerminalNodeImpl.java"
    "org/antlr/v4/runtime/tree/ErrorNodeImpl.java"
    "org/antlr/v4/runtime/tree/ParseTreeVisitor.java"
    "org/antlr/v4/runtime/tree/AbstractParseTreeVisitor.java"
    "org/antlr/v4/runtime/tree/TerminalNode.java"
    "org/antlr/v4/runtime/tree/ParseTreeWalker.java"
    "org/antlr/v4/runtime/tree/ParseTreeListener.java"
    "org/antlr/v4/runtime/tree/ParseTree.java"
    "org/antlr/v4/runtime/tree/ErrorNode.java"
    )

PATCH_FILE="MyChangesOn-${CURRENT_VERSION_USED}.patch"

cd "${SCRIPTDIR}" || exit

echo "- Get Antlr4"
[ -d antlr4_repo ] || git clone https://github.com/antlr/antlr4 antlr4_repo
ANTLR_REPO_DIR="${SCRIPTDIR}/antlr4_repo"

echo "- Get currently used version"
cd "${ANTLR_REPO_DIR}" || exit
git pull
git checkout ${CURRENT_VERSION_USED}

echo "- Copy currently used version"
for F in  "${PATCHED_FILES[@]}";
do
  cp "${ANTLR_REPO_DIR}/runtime/Java/src/${F}" "${MODULE_ROOT}/src/main/java/${F}"
done

echo "- Create patch"
cd "${PROJECT_ROOT}" || exit
rm "${PATCH_FILE}"
git diff -R antlr4-pvisitor/src/main/java/ > "${PATCH_FILE}"

echo "- Get latest version"
cd "${ANTLR_REPO_DIR}" || exit
git checkout master
LATEST_TAG=$(git tag | grep '^4\.[0-9]\+\(\.[0-9]\+\)\?$' | tail -n1)
git checkout ${LATEST_TAG}

echo "- Copy latest version"
for F in "${PATCHED_FILES[@]}";
do
  cp "${ANTLR_REPO_DIR}/runtime/Java/src/${F}" "${MODULE_ROOT}/src/main/java/${F}"
done

echo "- Apply patch"
cd "${PROJECT_ROOT}" || exit
git apply "${PATCH_FILE}"
